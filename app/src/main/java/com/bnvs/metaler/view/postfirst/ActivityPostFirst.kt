package com.bnvs.metaler.view.postfirst

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bnvs.metaler.R
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.databinding.ActivityPostFirstBinding
import com.bnvs.metaler.util.base.BaseActivity
import com.bnvs.metaler.view.postsecond.ActivityPostSecond
import kotlinx.android.synthetic.main.activity_post_first.*
import org.koin.android.ext.android.inject


class ActivityPostFirst : BaseActivity<ViewModelPostFirst>() {

    companion object {
        private const val TAG = "ActivityPostFirst"
        private const val REQUEST_ALBUM_IMAGE = 1
        private const val REQUEST_CAMERA_IMAGE = 2
        private const val REQUEST_RUN_TIME_PERMISSION = 100
    }

    override val viewModel: ViewModelPostFirst by inject()

    private val thumbnailAdapter = ThumbnailAdapter(
        itemClick = { adapterPosition ->
            showImageDeleteDialog(adapterPosition)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityPostFirstBinding>(
            this,
            R.layout.activity_post_first
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityPostFirst
            thumbnailRV.adapter = thumbnailAdapter
        }

        observeViewModel()
        getPostId()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        observeOpenCategorySelectionDialog()
        observeOpenPriceInputDialog()
        observeOpenImageSelectionDialog()
        observeOpenPostSecondActivity()
        observeFinishThisActivity()
    }

    private fun getPostId() {
        val postId = intent.getIntExtra("POST_ID", -1)
        Log.d(TAG, "intent 로 들어온 postId : $postId")
        if (postId == -1) {
            viewModel.setPostId(null)
        } else {
            viewModel.setPostId(postId)
        }
    }


    private fun openAlbum() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
        }.also { intent ->
            startActivityForResult(intent, REQUEST_ALBUM_IMAGE)
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)
            startActivityForResult(intent, REQUEST_CAMERA_IMAGE)
        }
    }

    private fun observeFinishThisActivity() {
        viewModel.finishThisActivity.observe(
            this,
            Observer { finish ->
                if (finish) {

                }
            }
        )
    }

    private fun observeOpenPostSecondActivity() {
        viewModel.openPostSecondActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {

                }
            }
        )
    }

    private fun observeOpenCategorySelectionDialog() {
        viewModel.openCategorySelectionDialog.observe(
            this,
            Observer { openDialog ->
                if (openDialog) {
                    showChooseCategoryDialog(viewModel.materialCategories.value)
                }
            }
        )
    }

    private fun showChooseCategoryDialog(categories: List<Category>?) {
        val list = categories?.map { it.name } ?: return
        val array = list.toTypedArray()

        AlertDialog.Builder(this@ActivityPostFirst)
            .setTitle("카테고리")
            .setItems(array) { _, which ->
                categories[which].let {
                    viewModel.setCategory(it.id, it.name)
                }
            }
            .show()
    }

    private fun observeOpenPriceInputDialog() {
        viewModel.openPriceInputDialog.observe(
            this,
            Observer { openDialog ->
                if (openDialog) {
                    showPriceInputDialog(viewModel.price.value)
                }
            }
        )
    }

    private fun showPriceInputDialog(price: Int?) {
        val content = layoutInflater.inflate(R.layout.dialog_price_input, null)
        if (price != null) {
            content.findViewById<EditText>(R.id.priceInputEditTxt).let {
                it.setText(price.toString())
            }
        }
        AlertDialog.Builder(this@ActivityPostFirst)
            .setTitle("가격 입력")
            .setView(content)
            .setPositiveButton("확인") { dialog, _ ->
                (dialog as Dialog).findViewById<EditText>(R.id.priceInputEditTxt).let {
                    val priceInput: String? = it.text.toString()
                    if (!priceInput.isNullOrBlank()) {
                        viewModel.setPrice(priceInput.toInt())
                    } else {
                        viewModel.setPrice(null)
                    }
                }
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    private fun observeOpenImageSelectionDialog() {
        viewModel.openImageSelectionDialog.observe(
            this,
            Observer { openDialog ->
                if (openDialog) {

                }
            }
        )
    }

    private fun showWhereToGetImageFromDialog() {
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

    private fun showTransparentLoadingLayer(b: Boolean) {
        if (b) {
            transparentLoadingLayer.bringToFront()
            transparentLoadingLayer.visibility = View.VISIBLE
        } else {
            transparentLoadingLayer.visibility = View.GONE
        }
    }

    private fun showGetCategoriesFailedToast(errorMessage: String) {
        makeToast("카테고리 목록을 조회하는데 실패했습니다 : $errorMessage")
    }

    private fun showPostDetailLoadFailedToast(errorMessage: String) {
        makeToast("게시물 내용을 불러오는데 실패했습니다 : $errorMessage")
    }

    private fun showUploadImageFailedToast(errorMessage: String) {
        makeToast("사진 업로드에 실패했습니다 : $errorMessage")
    }

    private fun showImageDeleteDialog(adapterPosition: Int) {
        AlertDialog.Builder(this@ActivityPostFirst)
            .setMessage("이미지를 삭제하시겠습니까?")
            .setPositiveButton("삭제") { _, _ ->
                // presenter.deleteImage(adapterPosition)
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    private fun showEmptyCategoryDialog() {
        // makeAlertDialog("카테고리를 입력해 주세요")
    }

    private fun showEmptyTitleDialog() {
        // makeAlertDialog("제목을 입력해 주세요")
    }

    private fun showEmptyPriceDialog() {
        //  makeAlertDialog("가격을 입력해 주세요")
    }

    private fun showEmptyPriceTypeDialog() {
        // makeAlertDialog("지불 방식을 선택해 주세요")
    }

    private fun showEmptyContentsDialog() {
        //makeAlertDialog("내용을 입력해 주세요")
    }

    private fun showPostSecondUi(
        categoryType: String,
        postId: Int?,
        addEditPostRequest: AddEditPostRequest
    ) {
        Intent(this, ActivityPostSecond::class.java).apply {
            putExtra("addEditPostRequest", addEditPostRequest)
            putExtra("CATEGORY_TYPE", categoryType)
            if (postId != null) {
                putExtra("POST_ID", postId.toString())
            }
            startActivity(this)
        }
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
                // presenter.getImageFromAlbum(this, data)
            }
        } else if (requestCode == REQUEST_CAMERA_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                // presenter.getImageFromCamera(this, data)
            }
        } else {
            makeToast("취소되었습니다")
        }
    }

}
