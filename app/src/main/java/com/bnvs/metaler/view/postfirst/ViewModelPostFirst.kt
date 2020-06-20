package com.bnvs.metaler.view.postfirst

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepository
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.postdetails.source.repository.PostDetailsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.BaseViewModel
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE

class ViewModelPostFirst(
    private val categoriesRepository: CategoriesRepository,
    private val addEditPostRepository: AddEditPostRepository,
    private val postDetailsRepository: PostDetailsRepository
) : BaseViewModel() {

    companion object {
        private const val MODE_ADD_POST = 0
        private const val MODE_EDIT_POST = 1
    }

    init {
        getPostFirstCategoryType()
    }

    private var postId: Int? = null
    private var mode = MODE_ADD_POST

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _categoryType = MutableLiveData<String>()
    val categoryType: LiveData<String> = _categoryType
    private val _materialCategories = MutableLiveData<List<Category>>()
    val materialCategories: LiveData<List<Category>> = _materialCategories

    // 게시물 작성 관련 데이터
    private val _categoryId = MutableLiveData<Int>()
    val categoryId: LiveData<Int> = _categoryId
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title
    private val _price = MutableLiveData<Int>()
    val price: LiveData<Int> = _price
    private val _priceType = MutableLiveData<String>()
    val priceType: LiveData<String> = _priceType
    private val _attachIds = MutableLiveData<List<Int>>()
    val attachIds: LiveData<List<Int>> = _attachIds

    fun setPostId(id: Int?) {
        if (id == null) {
            postId = id
            mode = MODE_ADD_POST
        } else {
            postId = id
            mode = MODE_EDIT_POST
            // loadPost
        }
    }

    private fun getPostFirstCategoryType() {
        categoriesRepository.getCategoryTypeCache(
            onSuccess = { categoryType ->
                _categoryId.value = categoryType
                loadCategories()
            },
            onFailure = {
                _errorToastMessage.apply {
                    value = "검색 타입을 알 수 없습니다"
                    value = clearStringValue()
                }
            }
        )
    }

    private fun loadCategories() {
        categoriesRepository.getCategories(
            onSuccess = { response ->
                response
                    .filter { it.type == "manufacture" }
                    .map { it.id }
                    .let { setCategoryType(it) }

                response
                    .filter { it.type == "materials" }
                    .let { _materialCategories.value = it }
            },
            onFailure = { e ->
                _errorToastMessage.apply {
                    value = NetworkUtil.getErrorMessage(e)
                    value = clearStringValue()
                }
            },
            handleError = { e ->
                _errorCode.apply {
                    value = e
                    value = NO_ERROR_TO_HANDLE
                }
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


}