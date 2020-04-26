package com.bnvs.metaler.ui.postfirst

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepository
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository
import com.bnvs.metaler.network.NetworkUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class PresenterPostFirst(
    private val categoryType: String?,
    private val postId: Int?,
    private val view: ContractPostFirst.View
) : ContractPostFirst.Presenter {

    private val addEditPostRepository = AddEditPostRepository()
    private val postDetailsRepository = PostDetailsRepository()
    private val categoriesRepository = CategoriesRepository()

    private var addEditPostRequest = AddEditPostRequest(
        null,
        null,
        null,
        null,
        null,
        mutableListOf(),
        mutableListOf()
    )

    private lateinit var context: Context
    private lateinit var categories: List<Category>

    override fun start() {
        if (categoryType == "MATERIALS") {
            view.showCategories()
        }
        if (postId != null) {
            populatePost(postId)
        }
    }

    override fun getCategories() {
        categoriesRepository.getCategories(
            onSuccess = { response ->
                categories = response
            },
            onFailure = { e ->
                view.showGetCategoriesFailedToast(NetworkUtil.getErrorMessage(e))
            }
        )
    }

    override fun populatePost(postId: Int) {
        postDetailsRepository.getPostDetails(
            postId,
            onSuccess = { response ->
                setCategory(response.category_id)
                setTitle(response.title)
                setPrice(response.price)
                setPriceType(response.price_type)
                setImage(response.attach_ids, response.attach_urls)
                setContents(response.content)
            },
            onFailure = { e ->
                view.showPostDetailLoadFailedToast(NetworkUtil.getErrorMessage(e))
            }
        )
    }

    override fun setCategory(categoryId: Int) {
        addEditPostRequest.category_id = categoryId
        if (categoryType == "MATERIALS") {
            val category = when (categoryId) {
                categories[0].id -> "적동/황동"
                3 -> "스테인리스"
                4 -> "알루미늄"
                5 -> "철"
                6 -> "공구"
                7 -> "화학 약품류"
                8 -> "니켈/티타늄"
                9 -> "기타"
                else -> return
            }
            view.setCategory(category)
        }
    }

    override fun setTitle(title: String) {
        addEditPostRequest.title = title
        view.setTitle(title)
    }

    override fun setPrice(price: Int) {
        addEditPostRequest.price = price
        view.setPrice(price)
    }

    override fun setPriceType(priceType: String) {
        addEditPostRequest.price_type = priceType
        when (priceType) {
            "card" -> view.setCardButton()
            "cash" -> view.setCashButton()
            else -> return
        }
    }

    override fun setImage(attachIds: List<Int>, attachUrls: List<String>) {
        addEditPostRequest.attach_ids = attachIds.toMutableList()
        if (attachUrls.isEmpty()) {
            view.setImageGuideText(true)
        } else {
            view.setImageGuideText(false)
            view.setImages(attachUrls)
        }
    }

    override fun addImage(attachId: Int, imageUrl: String) {
        Log.d("addImage", "이미지 추가됨")
        if (addEditPostRequest.attach_ids.isEmpty()) {
            view.setImageGuideText(false)
        }
        addEditPostRequest.attach_ids.add(attachId)
        view.addImage(imageUrl)
    }

    override fun deleteImage(imageIndex: Int) {
        addEditPostRequest.attach_ids.removeAt(imageIndex)
        view.deleteImage(imageIndex)
    }

    override fun setContents(contents: String) {
        addEditPostRequest.content = contents
        view.setContents(contents)
    }

    override fun openWhereToGetImageFrom() {
        view.showWhereToGetImageFromDialog()
    }

    override fun getImageFromAlbum(context: Context, data: Intent) {
        this.context = context
        Log.d("getImageFromAlbum", "이미지 앨범에서 가져옴")
        val clipData = data.clipData
        if (clipData != null) {
            Log.d("clipData", "이미지 여러장 가져오는데 성공함")
            for (i in 0..clipData.itemCount) {
                val imageUri = clipData.getItemAt(i).uri
                uploadImage(getFileFromUri(imageUri))
            }
        } else {
            val imageUri = data.data
            uploadImage(getFileFromUri(imageUri))
        }
    }

    override fun getImageFromCamera(context: Context, data: Intent) {
        this.context = context
        deleteCache(context.cacheDir)
        val bitmap: Bitmap = data.extras!!.get("data") as Bitmap
        val path = saveBitmapToCache(bitmap)
        uploadImage(File(path))
    }

    private fun getFileFromUri(imageUri: Uri?): File {
        deleteCache(context.cacheDir)
        return if (imageUri != null) {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream!!.close()
            val path = saveBitmapToCache(bitmap)
            File(path)
        } else {
            File("")
        }
    }

    private fun saveBitmapToCache(bitmap: Bitmap): String {
        val cacheFile = File(context.cacheDir, "cache_image.jpg")
        cacheFile.createNewFile()
        val outputStream = FileOutputStream(cacheFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        return cacheFile.absolutePath
    }

    private fun resize(file: File): File {
        Log.d("resize", "리사이징함")
        val options = BitmapFactory.Options().apply {
            inSampleSize = 4
        }
        val bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
        Bitmap.createScaledBitmap(
            bitmap,
            bitmap.width / 10,
            bitmap.height / 10,
            false
        )
        deleteCache(context.cacheDir)
        return File(saveBitmapToCache(bitmap))
    }

    private fun deleteCache(cacheDir: File): Boolean {
        Log.d("deleteCache", "캐시 지움")
        if (cacheDir.isDirectory) {
            val files = cacheDir.list()
            for (file in files) {
                val deleteSuccess = deleteCache(File(cacheDir, file))
                if (!deleteSuccess) {
                    return false
                }
            }
        }
        return cacheDir.delete()
    }

    override fun uploadImage(file: File) {
        while (true) {
            if (file.length() > 3145728) {
                Log.d("uploadImage", "파일 크기 ${file.length()}Bytes")
                resize(file)
            } else {
                break
            }
        }

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("upload", file.name, requestBody)
        addEditPostRepository.uploadFile(
            part,
            onSuccess = { response ->
                Log.d("uploadImage", "서버에 이미지 업로드 성공")
                addImage(response.attach_id, response.url)
            },
            onFailure = { e ->
                Log.d("uploadImage", "파일 크기 ${file.length()}Bytes")
                view.showUploadImageFailedToast(NetworkUtil.getErrorMessage(e))
            }
        )
    }

    override fun getAttachUrl() {

    }

    override fun setAddEditPostRequest() {

    }

    override fun openPostSecond(contents: JSONObject) {
        completeAddEditPostRequestExceptTags(contents)
        Log.d("addEditPostRequest 태그빼고 완성", addEditPostRequest.toString())
        if (addEditPostRequest.category_id == null) {
            view.showEmptyCategoryDialog()
            return
        }
        if (addEditPostRequest.title.isNullOrBlank()) {
            view.showEmptyTitleDialog()
            return
        }
        if (addEditPostRequest.price == null) {
            view.showEmptyPriceDialog()
            return
        }
        if (addEditPostRequest.price_type.isNullOrBlank()) {
            view.showEmptyPriceTypeDialog()
            return
        }
        if (addEditPostRequest.content.isNullOrBlank()) {
            view.showEmptyContentsDialog()
            return
        }

        view.showPostSecondUi(addEditPostRequest)
    }
}