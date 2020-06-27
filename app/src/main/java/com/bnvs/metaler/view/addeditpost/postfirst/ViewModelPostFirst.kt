package com.bnvs.metaler.view.addeditpost.postfirst

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.addeditpost.model.AddEditPostLocalCache
import com.bnvs.metaler.data.addeditpost.model.PostTag
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepository
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.postdetails.model.AttachImage
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.BaseAddEditViewModel
import com.bnvs.metaler.util.constants.MODE_ADD_POST
import com.bnvs.metaler.util.constants.MODE_EDIT_POST
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class ViewModelPostFirst(
    application: Application,
    private val categoriesRepository: CategoriesRepository,
    private val addEditPostRepository: AddEditPostRepository,
    private val postDetailsRepository: PostDetailsRepository
) : BaseAddEditViewModel(application) {

    private val context = application.applicationContext

    private var postId: Int? = null
    private var mode = MODE_ADD_POST

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _categoryType = MutableLiveData<String>()
    val categoryType: LiveData<String> = _categoryType
    private val categories = mutableListOf<Category>()
    private val _materialCategories = MutableLiveData<List<Category>>()
    val materialCategories: LiveData<List<Category>> = _materialCategories
    private val _selectedMaterialsCategoryName = MutableLiveData<String>().apply { value = "카테고리" }
    val selectedMaterialsCategoryName: LiveData<String> = _selectedMaterialsCategoryName

    // 게시물 작성 관련 데이터
    private val _categoryId = MutableLiveData<Int>()
    val categoryId: LiveData<Int> = _categoryId
    val title = MutableLiveData<String>()
    private val _price = MutableLiveData<Int>()
    val price: LiveData<Int> = _price
    private val _priceType = MutableLiveData<String>()
    val priceType: LiveData<String> = _priceType
    val content = MutableLiveData<String>()
    private val _attachIds = MutableLiveData<List<AttachImage>>().apply { value = listOf() }
    val attachIds: LiveData<List<AttachImage>> = _attachIds
    private val tags = MutableLiveData<List<PostTag>>().apply { value = listOf() }

    // 지불방식 관련 데이터
    private val _priceTypeChecked = MutableLiveData<Map<String, Boolean>>().apply {
        value = mapOf("card" to false, "cash" to false)
    }
    val priceTypeChecked: LiveData<Map<String, Boolean>> = _priceTypeChecked

    // 다이얼로그 띄우기 관련 데이터
    private val _openCategorySelectionDialog = MutableLiveData<Boolean>().apply { value = false }
    val openCategorySelectionDialog: LiveData<Boolean> = _openCategorySelectionDialog
    private val _openPriceInputDialog = MutableLiveData<Boolean>().apply { value = false }
    val openPriceInputDialog: LiveData<Boolean> = _openPriceInputDialog
    private val _openImageSelectionDialog = MutableLiveData<Boolean>().apply { value = false }
    val openImageSelectionDialog: LiveData<Boolean> = _openImageSelectionDialog

    // Activity 종료 관련 데이터
    private val _finishThisActivity = MutableLiveData<Boolean>().apply { value = false }
    val finishThisActivity: LiveData<Boolean> = _finishThisActivity

    // postSecond Activity 실행관련 데이터
    private val _openPostSecondActivity = MutableLiveData<Boolean>().apply { value = false }
    val openPostSecondActivity: LiveData<Boolean> = _openPostSecondActivity

    // input focus  관련 데이터
    private val _focusToView = MutableLiveData<String>()
    val focusToView: LiveData<String> = _focusToView


    init {
        loadCategories()
    }

    private fun loadCategories() {
        categoriesRepository.getCategories(
            onSuccess = { response ->
                categories.apply {
                    clear()
                    addAll(response)
                }
                response
                    .filter { it.type == "materials" }
                    .let { _materialCategories.value = it }
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    fun setPostId(id: Int?) {
        if (id == null) {
            postId = id
            mode = MODE_ADD_POST
            getCategoryTypeCache()
        } else {
            postId = id
            mode = MODE_EDIT_POST
            loadPostFromPostId()
        }
    }

    fun getPostId(): Int? {
        return postId
    }

    private fun getCategoryTypeCache() {
        categoriesRepository.getCategoryTypeCache(
            onSuccess = { categoryType ->
                _categoryId.value = categoryType
                setCategoryType()
            },
            onFailure = {
                _errorToastMessage.setMessage("글쓰기 타입을 알 수 없습니다")
            }
        )
    }

    private fun loadPostFromPostId() {
        postDetailsRepository.getPostDetails(
            postId = postId!!,
            onSuccess = { response ->
                response.let {
                    _categoryId.value = it.category_id
                    setCategoryType()
                    if (categoryType.value == "materials") {
                        _selectedMaterialsCategoryName.value =
                            materialCategories.value?.first { materialCategory ->
                                materialCategory.id == it.category_id
                            }?.name
                    }
                    title.value = it.title
                    _price.value = it.price
                    setPriceType(it.price_type)
                    content.value = it.content
                    _attachIds.value = it.attachs
                    tags.value = it.tags
                }
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    private fun setCategoryType() {
        val manufactureCategories = categories.filter { it.type == "manufacture" }.map { it.id }
        if (manufactureCategories.contains(categoryId.value)) {
            _categoryType.value = "manufacture"
        } else {
            _categoryType.value = "materials"
        }
    }

    fun setCategory(categoryId: Int, categoryName: String) {
        _categoryId.value = categoryId
        _selectedMaterialsCategoryName.value = categoryName
    }

    fun setPrice(price: Int?) {
        _price.value = price
    }

    fun setPriceType(priceType: String) {
        when (priceType) {
            "card" -> {
                _priceType.value = priceType
                _priceTypeChecked.value = mapOf("card" to true, "cash" to false)
            }
            "cash" -> {
                _priceType.value = priceType
                _priceTypeChecked.value = mapOf("card" to false, "cash" to true)
            }
        }
    }

    fun finishPostFirstActivity() {
        _finishThisActivity.enable()
    }

    fun openCategorySelectionDialog() {
        _openCategorySelectionDialog.enable()
    }

    fun openPriceInputDialog() {
        _openPriceInputDialog.enable()
    }

    fun openImageSelectionDialog() {
        attachIds.value?.let {
            Log.d("사진 개수", "${it.size}")
            if (it.size > 4) {
                _errorDialogMessage.setMessage("사진은 최대 5장까지 첨부 가능합니다")
                return
            } else {
                _openImageSelectionDialog.enable()
            }
        }
    }

    private fun setLoadingView(b: Boolean) {
        _isLoading.value = b
    }

    fun deleteImage(imageIndex: Int) {
        _attachIds.value =
            attachIds.value?.filterIndexed { index, _ -> index != imageIndex }
    }

    private fun addImage(image: AttachImage) {
        _attachIds.value = attachIds.value?.plus(image) ?: listOf(image)
    }

    fun getImageFromAlbum(data: Intent) {
        setLoadingView(true)
        Log.d("getImageFromAlbum", "이미지 앨범에서 가져옴")
        val clipData = data.clipData
        if (clipData != null) {
            Log.d("clipData", "이미지 여러장 가져오는데 성공함")
            for (i in 0..clipData.itemCount) {
                val imageUri = clipData.getItemAt(i).uri
                getTempFileName(imageUri)
                uploadImage(getFileFromUri(imageUri))
            }
        } else {
            data.data?.let { imageUri ->
                getTempFileName(imageUri)
                uploadImage(getFileFromUri(imageUri))
            }
        }
    }

    private var tempFileName = ""

    private fun getTempFileName(uri: Uri) {
        context.contentResolver.query(uri, null, null, null, null)
            ?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                tempFileName = "${cursor.getString(nameIndex).split(".")[0]}.jpg"
            }
    }

    private fun setTempFileName() {
        tempFileName = "${System.currentTimeMillis()}.jpg"
    }

    fun getImageFromCamera(data: Intent) {
        setLoadingView(true)
        deleteCache(context.cacheDir)
        setTempFileName()
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
        val cacheFile = File(context.cacheDir, tempFileName)
        cacheFile.createNewFile()
        val outputStream = FileOutputStream(cacheFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        return cacheFile.absolutePath
    }

    private fun resize(file: File): File {
        Log.d("resize", "리사이징함")
        val options = BitmapFactory.Options().apply {
            inSampleSize = 2
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
            if (files != null) {
                for (file in files) {
                    val deleteSuccess = deleteCache(File(cacheDir, file))
                    if (!deleteSuccess) {
                        return false
                    }
                }
            }
        }
        return cacheDir.delete()
    }

    private fun uploadImage(file: File) {
        while (true) {
            if (file.length() > 3145728) {
                Log.d("uploadImage", "파일 크기 ${file.length()}Bytes")
                resize(file)
            } else {
                Log.d("uploadImage", "파일 크기 ${file.length()}Bytes")
                break
            }
        }

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("upload", file.name, requestBody)
        addEditPostRepository.uploadFile(
            part,
            onSuccess = { response ->
                Log.d("uploadImage", "서버에 이미지 업로드 성공")
                setLoadingView(false)
                addImage(AttachImage(response.attach_id, response.url))
            },
            onFailure = { e ->
                Log.d("uploadImage", "파일 크기 ${file.length()}Bytes")
                setLoadingView(false)
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            }
        )
    }

    fun completePostFirst() {
        if (!checkCategoryInput()) {
            _focusToView.setMessage("CATEGORY")
            _openCategorySelectionDialog.enable()
            _errorDialogMessage.setMessage("카테고리를 입력해 주세요")
            return
        }
        if (!checkTitleInput()) {
            _focusToView.setMessage("TITLE")
            _errorDialogMessage.setMessage("제목을 입력해 주세요")
            return
        }
        if (!checkPriceInput()) {
            _focusToView.setMessage("PRICE")
            _openPriceInputDialog.enable()
            _errorDialogMessage.setMessage("가격을 입력해 주세요")
            return
        }
        if (!checkPriceTypeInput()) {
            _focusToView.setMessage("PRICE_TYPE")
            _errorDialogMessage.setMessage("지불 방식을 선택해 주세요")
            return
        }
        if (!checkContentInput()) {
            _focusToView.setMessage("CONTENT")
            _errorDialogMessage.setMessage("내용을 입력해 주세요")
            return
        }
        savePostFirstCacheData()
        openPostSecondActivity()
    }

    private fun checkCategoryInput(): Boolean {
        return when (categoryType.value) {
            "materials" -> {
                val totalCategoryId = categories.first { it.type == "total" }.id
                categoryId.value != totalCategoryId
            }
            "manufacture" -> {
                true
            }
            else -> false
        }
    }

    private fun checkTitleInput(): Boolean {
        return !title.value.isNullOrBlank()
    }

    private fun checkPriceInput(): Boolean {
        return price.value != null
    }

    private fun checkPriceTypeInput(): Boolean {
        return priceType.value == "card" || priceType.value == "cash"
    }

    private fun checkContentInput(): Boolean {
        return !content.value.isNullOrBlank()
    }

    private fun openPostSecondActivity() {
        _openPostSecondActivity.enable()
    }

    private fun savePostFirstCacheData() {
        AddEditPostLocalCache(
            postId,
            mode,
            categoryType.value ?: "null",
            categoryId.value ?: -1,
            title.value?.trim() ?: "null",
            price.value ?: -1,
            priceType.value ?: "null",
            content.value?.trim() ?: "null",
            attachIds.value ?: listOf(),
            tags.value ?: listOf()
        ).let {
            Log.d("게시글 작성 1단계 캐시", it.toString())
            when (mode) {
                MODE_ADD_POST -> {
                    addEditPostRepository.saveAddPostCache(it)
                }
                MODE_EDIT_POST -> {
                    addEditPostRepository.saveEditPostCache(postId!!, it)
                }
                else -> {
                    _errorToastMessage.setMessage("게시글 작성에 문제가 생겼습니다")
                    return
                }
            }
        }
    }
}