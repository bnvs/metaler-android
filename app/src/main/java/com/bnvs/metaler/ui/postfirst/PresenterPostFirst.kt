package com.bnvs.metaler.ui.postfirst

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.bnvs.metaler.BuildConfig
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.model.UploadFileResponse
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepository
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.network.RetrofitInterface
import com.bnvs.metaler.util.RealPathUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class PresenterPostFirst(
    private val categoryType: String,
    private val postId: Int,
    private val view: ContractPostFirst.View
) : ContractPostFirst.Presenter {

    private val addEditPostRepository = AddEditPostRepository()
    private val postDetailsRepository = PostDetailsRepository()

    private val realPathUtil = RealPathUtil()

    private var addEditPostRequest = AddEditPostRequest(
        null,
        null,
        null,
        null,
        null,
        mutableListOf(),
        mutableListOf()
    )

    override fun start() {
        if (categoryType == "MATERIALS") {
            view.showCategories()
        }
        if (postId != 0) {
            populatePost(postId)
        }
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
                view.showPostDetailLoadFailedDialog(NetworkUtil.getErrorMessage(e))
            }
        )
    }

    override fun setCategory(categoryId: Int) {
        addEditPostRequest.category_id = categoryId
        if (categoryType == "MATERIALS") {
            val category = when (categoryId) {
                2 -> "적동/황동"
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

    override fun getImageFromAlbumIntent(context: Context): Intent {
        return Intent(Intent.ACTION_GET_CONTENT).apply {
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
        }
    }

    override fun getImageFromAlbum(context: Context, data: Intent) {
        Log.d("getImageFromAlbum", "이미지 앨범에서 가져옴")
        val clipData = data.clipData
        if (clipData != null) {
            Log.d("clipData", "이미지 여러장 가져오는데 성공함")
            for (i in 0..clipData.itemCount) {
                val imageUri = clipData.getItemAt(i).uri
                deleteCache(context.cacheDir)
                if (imageUri != null) {
                    val inputStream = context.contentResolver.openInputStream(imageUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream!!.close()
                    val path = saveBitmapToCache(context, bitmap)
                    val file = File(path)
                    uploadImage(file)
                }
            }
        } else {
            deleteCache(context.cacheDir)
            val imageUri = data.data
            if (imageUri != null) {
                val inputStream = context.contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream!!.close()
                val path = saveBitmapToCache(context, bitmap)
                val file = File(path)
                uploadImage(file)
            }
        }
    }

    private fun saveBitmapToCache(context: Context, bitmap: Bitmap): String {
        val cacheFile = File(context.cacheDir, "cache_image")
        cacheFile.createNewFile()
        val outputStream = FileOutputStream(cacheFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        return cacheFile.absolutePath
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

    override fun getImageFromCamera() {

    }

    override fun uploadImage(file: File) {
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("upload", file.name, requestBody)
        Log.d("uploadImage", "이미지 업로드 로직 타고 들어옴")
        Log.d("uploadImage", "이미지 업로드 로직 타고 들어온 파일 $file")
        Log.d("uploadImage", "이미지 업로드 로직 타고 들어온 파일 $requestBody")
        Log.d("uploadImage", "이미지 업로드 로직 타고 들어온 파일 $part")
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://file.metaler.kr/").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(RetrofitInterface::class.java)

        service.uploadFile(part).enqueue(object : Callback<UploadFileResponse> {
            override fun onResponse(
                call: Call<UploadFileResponse>,
                response: Response<UploadFileResponse>
            ) {
                Log.d("uploadImage", "서버에 이미지 업로드 성공${response.body()}")
            }

            override fun onFailure(call: Call<UploadFileResponse>, t: Throwable) {
                Log.d("uploadImage", "서버에 이미지 업로드 실패함$t")
            }
        })

        view.test(file)
        /*addEditPostRepository.uploadFile(
            part,
            onSuccess = { response ->
                Log.d("uploadImage", "서버에 이미지 업로드 성공")
                addImage(response.attach_id, response.url)
            },
            onFailure = { e ->
                Log.d("uploadImage", "서버에 이미지 업로드 실패함")
                view.showUploadImageFailedDialog(NetworkUtil.getErrorMessage(e))
            }
        )*/
    }

    override fun getAttachUrl() {

    }

    override fun openPostSecond() {
        view.showPostSecondUi()
    }
}