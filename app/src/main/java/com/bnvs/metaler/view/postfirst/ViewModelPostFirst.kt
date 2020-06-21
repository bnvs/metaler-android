package com.bnvs.metaler.view.postfirst

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepository
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.BaseViewModel

class ViewModelPostFirst(
    private val categoriesRepository: CategoriesRepository,
    private val addEditPostRepository: AddEditPostRepository,
    private val postDetailsRepository: PostDetailsRepository
) : BaseViewModel() {

    companion object {
        private const val MODE_ADD_POST = 0
        private const val MODE_EDIT_POST = 1
    }

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
    val content = MutableLiveData<String>()
    private val _priceType = MutableLiveData<String>()
    val priceType: LiveData<String> = _priceType
    private val _attachIds = MutableLiveData<List<Int>>().apply { value = listOf() }
    val attachIds: LiveData<List<Int>> = _attachIds

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
    private val _focusOnCategoryInput = MutableLiveData<Boolean>().apply { value = false }
    val focusOnCategoryInput: LiveData<Boolean> = _focusOnCategoryInput
    private val _focusOnTitleInput = MutableLiveData<Boolean>().apply { value = false }
    val focusOnTitleInput: LiveData<Boolean> = _focusOnTitleInput
    private val _focusOnPriceInput = MutableLiveData<Boolean>().apply { value = false }
    val focusOnPriceInput: LiveData<Boolean> = _focusOnPriceInput
    private val _focusOnPriceTypeInput = MutableLiveData<Boolean>().apply { value = false }
    val focusOnPriceTypeInput: LiveData<Boolean> = _focusOnPriceTypeInput
    private val _focusOnContentInput = MutableLiveData<Boolean>().apply { value = false }
    val focusOnContentInput: LiveData<Boolean> = _focusOnContentInput

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
            // loadPost from postId
        }
    }

    private fun getCategoryTypeCache() {
        categoriesRepository.getCategoryTypeCache(
            onSuccess = { categoryType ->
                _categoryId.value = categoryType
                categories.filter { it.type == "manufacture" }.map { it.id }.let {
                    setCategoryType(it)
                }
            },
            onFailure = {
                _errorToastMessage.setMessage("글쓰기 타입을 알 수 없습니다")
            }
        )
    }

    private fun setCategoryType(manufactureCategories: List<Int>) {
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
            if (it.size > 5) {
                _errorDialogMessage.setMessage("사진은 5장까지 첨부 가능합니다")
                return
            }
        }
        _openImageSelectionDialog.enable()
    }

    fun openPostSecondActivity() {
        _openPostSecondActivity.enable()
    }

    fun completePostFirst() {

    }

}