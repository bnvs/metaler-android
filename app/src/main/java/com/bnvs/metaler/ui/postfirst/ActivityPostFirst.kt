package com.bnvs.metaler.ui.postfirst

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bnvs.metaler.R
import kotlinx.android.synthetic.main.activity_post_first.*

class ActivityPostFirst : AppCompatActivity(), ContractPostFirst.View {

    private val TAG = "ActivityPostFirst"
    override lateinit var presenter: ContractPostFirst.Presenter
    private val thumbnailAdapter = ThumbnailAdapter(
        itemClick = { adapterPosition ->
            showImageDeleteDialog(adapterPosition)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_first)

        val categoryType = intent.getStringExtra("CATEGORY_TYPE")
        val postId = intent.getIntExtra("POST_ID", 0)

        presenter = PresenterPostFirst(categoryType, postId, this)

        thumbnailRV.adapter = thumbnailAdapter

        initClickListeners()

        presenter.run {
            start()
        }
    }

    override fun showCategories() {
        materialsCategory.visibility = View.VISIBLE
    }

    override fun setCategory(category: String) {
        categoryTxt.text = category
    }

    override fun setTitle(title: String) {
        titleInput.setText(title)
    }

    override fun setPrice(price: Int) {
        priceInput.setText(price)
    }

    override fun setCardButton() {
        setButtonEnabled(cardBtn, true)
        setButtonEnabled(cashBtn, false)
    }

    override fun setCashButton() {
        setButtonEnabled(cardBtn, false)
        setButtonEnabled(cashBtn, true)
    }

    private fun setButtonEnabled(button: TextView, b: Boolean) {
        if (b) {
            button.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            button.setTextColor(ContextCompat.getColor(this, R.color.colorPurple))
        } else {
            button.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            button.setTextColor(ContextCompat.getColor(this, R.color.colorLightGrey))
        }
    }

    override fun setImageGuideText(b: Boolean) {
        when (b) {
            true -> {
                addImgGuideTxt.visibility = View.VISIBLE
                thumbnailRV.visibility = View.GONE
            }
            false -> {
                addImgGuideTxt.visibility = View.GONE
                thumbnailRV.visibility = View.VISIBLE
            }
        }
    }

    override fun setImages(images: List<String>) {
        thumbnailAdapter.setImages(images)
    }

    override fun addImage(imageUrl: String) {
        thumbnailAdapter.addImage(imageUrl)
    }

    override fun deleteImage(imageIndex: Int) {
        thumbnailAdapter.deleteImage(imageIndex)
    }

    override fun setContents(contents: String) {
        contentGuideTxt.setText(contents)
    }

    override fun showWhereToGetImageFromDialog() {
        val array = arrayOf("사진", "카메라")
        AlertDialog.Builder(this@ActivityPostFirst)
            .setTitle("사진 선택")
            .setItems(array) { _, which ->
                when (array[which]) {
                    "사진" -> {
                        checkPermission()
                        startActivityForResult(
                            presenter.getImageFromAlbumIntent(this@ActivityPostFirst),
                            1
                        )
                    }
                    "카메라" -> {
                    }
                }
            }
            .show()
    }

    override fun showPostDetailLoadFailedDialog(errorMessage: String) {
    }

    override fun showUploadImageFailedDialog(errorMessage: String) {

    }

    override fun showImageDeleteDialog(adapterPosition: Int) {
        AlertDialog.Builder(applicationContext)
            .setMessage("이미지를 삭제하시겠습니까?")
            .setPositiveButton("삭제") { _, _ ->
                presenter.deleteImage(adapterPosition)
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    override fun showEmptyTitleDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyPriceDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyPriceTypeDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyContentsDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPostSecondUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initClickListeners() {
        setAppBarButtons()
        setPriceTypeButtons()
    }

    private fun setAppBarButtons() {
        backBtn.setOnClickListener { finish() }
        cameraBtn.setOnClickListener { presenter.openWhereToGetImageFrom() }
        nextBtn.setOnClickListener { presenter.openPostSecond() }
    }

    private fun setPriceTypeButtons() {

    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@ActivityPostFirst,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )
            return
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                presenter.getImageFromAlbum(data)
            }
        }
    }
}
