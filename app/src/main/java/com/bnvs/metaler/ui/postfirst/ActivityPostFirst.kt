package com.bnvs.metaler.ui.postfirst

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bnvs.metaler.R
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import kotlinx.android.synthetic.main.activity_post_first.*

class ActivityPostFirst : AppCompatActivity(), ContractPostFirst.View {

    companion object {
        private const val TAG = "ActivityPostFirst"
        private const val REQUEST_ALBUM_IMAGE = 1
        private const val REQUEST_CAMERA_IMAGE = 2
        private const val REQUEST_RUN_TIME_PERMISSION = 100
    }

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
        val postIdString = intent.getStringExtra("POST_ID")
        var postId: Int? = null
        if (postIdString != null) {
            postId = postIdString.toInt()
        }
        presenter = PresenterPostFirst(categoryType, postId, this)

        thumbnailRV.adapter = thumbnailAdapter

        initClickListeners()

        checkRunTimePermission()

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
            button.setBackgroundResource(R.drawable.purple_rounding_border)
            button.setTextColor(ContextCompat.getColor(this, R.color.colorPurple))
        } else {
            button.setBackgroundResource(R.drawable.lightgrey_rounding_border)
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

    override fun openAlbum() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
        }.also { intent ->
            startActivityForResult(intent, REQUEST_ALBUM_IMAGE)
        }
    }

    override fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)
            startActivityForResult(intent, REQUEST_CAMERA_IMAGE)
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

    override fun showChooseCategoryDialog(categories: List<JSONObject>) {
        val list = mutableListOf<String>()
        for (category in categories) {
            list.add(category.getString("name"))
        }
        val array = list.toTypedArray()
        AlertDialog.Builder(this@ActivityPostFirst)
            .setTitle("카테고리")
            .setItems(array) { _, which ->
                val categoryId = categories[which].getInt("id")
                presenter.setCategory(categoryId)
            }
            .show()
    }

    override fun showWhereToGetImageFromDialog() {
        val array = arrayOf("사진", "카메라")
        AlertDialog.Builder(this@ActivityPostFirst)
            .setTitle("사진 선택")
            .setItems(array) { _, which ->
                when (array[which]) {
                    "사진" -> {
                        if (isPermissionGranted()) {
                            openAlbum()
                        } else {
                            checkRunTimePermission()
                        }
                    }
                    "카메라" -> {
                        if (isPermissionGranted()) {
                            openCamera()
                        } else {
                            checkRunTimePermission()
                        }
                    }
                }
            }
            .show()
    }

    override fun showGetCategoriesFailedToast(errorMessage: String) {
        makeToast("카테고리 목록을 조회하는데 실패했습니다 : $errorMessage")
    }

    override fun showPostDetailLoadFailedToast(errorMessage: String) {
        makeToast("게시물 내용을 불러오는데 실패했습니다 : $errorMessage")
    }

    override fun showUploadImageFailedToast(errorMessage: String) {
        makeToast("사진 업로드에 실패했습니다 : $errorMessage")
    }

    override fun showImageDeleteDialog(adapterPosition: Int) {
        AlertDialog.Builder(this@ActivityPostFirst)
            .setMessage("이미지를 삭제하시겠습니까?")
            .setPositiveButton("삭제") { _, _ ->
                presenter.deleteImage(adapterPosition)
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    override fun showEmptyCategoryDialog() {
        makeAlertDialog("카테고리를 입력해 주세요")
    }

    override fun showEmptyTitleDialog() {
        makeAlertDialog("제목을 입력해 주세요")
    }

    override fun showEmptyPriceDialog() {
        makeAlertDialog("가격을 입력해 주세요")
    }

    override fun showEmptyPriceTypeDialog() {
        makeAlertDialog("지불 방식을 선택해 주세요")
    }

    override fun showEmptyContentsDialog() {
        makeAlertDialog("내용을 입력해 주세요")
    }

    override fun showPostSecondUi(addEditPostRequest: AddEditPostRequest) {

    }

    private fun initClickListeners() {
        setAppBarButtons()
        setPriceTypeButtons()
    }

    private fun setAppBarButtons() {
        backBtn.setOnClickListener { finish() }
        cameraBtn.setOnClickListener { presenter.openWhereToGetImageFrom() }
        nextBtn.setOnClickListener {
            val contents = JSONObject().apply {
                put("title", titleInput.text.toString())
                put("price", priceInput.text.toString())
                put("content", contentGuideTxt.text.toString())
            }
            presenter.openPostSecond(contents)
        }
    }

    private fun setPriceTypeButtons() {
        cardBtn.setOnClickListener { presenter.setPriceType("card") }
        cashBtn.setOnClickListener { presenter.setPriceType("cash") }
    }

    private fun checkRunTimePermission() {
        if (!isPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this@ActivityPostFirst,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ),
                REQUEST_RUN_TIME_PERMISSION
            )
            return
        }
    }

    private fun isPermissionGranted(): Boolean {
        return !(ActivityCompat.checkSelfPermission(
            this@ActivityPostFirst,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this@ActivityPostFirst,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            this@ActivityPostFirst,
            Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ALBUM_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                presenter.getImageFromAlbum(this, data)
            }
        } else if (requestCode == REQUEST_CAMERA_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                presenter.getImageFromCamera(this, data)
            }
        } else {
            makeToast("취소되었습니다")
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityPostFirst, message, Toast.LENGTH_LONG).show()
    }

    private fun makeAlertDialog(message: String) {
        AlertDialog.Builder(this@ActivityPostFirst)
            .setTitle("알림")
            .setMessage(message)
            .setPositiveButton("확인") { _, _ ->
            }
            .show()
    }
}
