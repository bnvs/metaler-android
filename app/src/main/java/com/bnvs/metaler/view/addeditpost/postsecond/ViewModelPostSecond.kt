package com.bnvs.metaler.view.addeditpost.postsecond

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.addeditpost.model.AddEditPostLocalCache
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepository
import com.bnvs.metaler.data.tags.source.repository.TagsRepository
import com.bnvs.metaler.util.base.BaseViewModel
import com.bnvs.metaler.util.constants.MODE_ADD_POST
import com.bnvs.metaler.util.constants.MODE_EDIT_POST

class ViewModelPostSecond(
    private val tagsRepository: TagsRepository,
    private val addEditPostRepository: AddEditPostRepository
) : BaseViewModel() {

    private var postId: Int? = null
    private var mode = MODE_ADD_POST

    private val _categoryType = MutableLiveData<String>()
    val categoryType: LiveData<String> = _categoryType

    private val addEditPostCache = MutableLiveData<AddEditPostLocalCache>()

    private val _storeTags = MutableLiveData<List<String>>()
    val storeTags: LiveData<List<String>> = _storeTags
    private val _workTags = MutableLiveData<List<String>>()
    val workTags: LiveData<List<String>> = _workTags
    private val _etcTags = MutableLiveData<List<String>>()
    val etcTags: LiveData<List<String>> = _etcTags

    // 뒤로가기
    private val _backToPostFirstActivity = MutableLiveData<Boolean>().apply { value = false }
    val backToPostFirstActivity: LiveData<Boolean> = _backToPostFirstActivity
    private val _completeAddEditPostActivity = MutableLiveData<Boolean>().apply { value = false }
    val completeAddEditPostActivity: LiveData<Boolean> = _completeAddEditPostActivity

    // input focus  관련 데이터
    private val _focusToView = MutableLiveData<String>()
    val focusToView: LiveData<String> = _focusToView

    fun setPostId(postId: Int?) {
        if (postId == null) {
            this.postId = postId
            mode = MODE_ADD_POST
            getPostFirstCacheData()
        } else {
            this.postId = postId
            mode = MODE_EDIT_POST
            getPostFirstCacheData()
        }
    }

    private fun getPostFirstCacheData() {
        when (mode) {
            MODE_ADD_POST -> {
                addEditPostRepository.getAddPostCache(
                    onSuccess = { response ->
                        response.let {
                            addEditPostCache.value = it
                            _categoryType.value = it.category_type
                            setTagsFromCache()
                        }
                    },
                    onFailure = { _errorToastMessage.setMessage("게시물 작성 1단계 내용을 불러오는 데 실패했습니다") }
                )
            }
            MODE_EDIT_POST -> {
                addEditPostRepository.getEditPostCache(
                    postId!!,
                    onSuccess = { response ->
                        response.let {
                            addEditPostCache.value = it
                            _categoryType.value = it.category_type
                            setTagsFromCache()
                        }
                    },
                    onFailure = { _errorToastMessage.setMessage("게시물 수정 1단계 내용을 불러오는 데 실패했습니다") }
                )
            }
        }
    }

    private fun setTagsFromCache() {
        when (categoryType.value) {
            "materials" -> {
                addEditPostCache.value?.tags?.let { postTags ->
                    _storeTags.value = postTags.filter { it.type == "store" }.map { it.name }
                    _etcTags.value = postTags.filter { it.type == "etc" }.map { it.name }
                }
            }
            "manufacture" -> {
                addEditPostCache.value?.tags?.let { postTags ->
                    _storeTags.value = postTags.filter { it.type == "store" }.map { it.name }
                    _workTags.value = postTags.filter { it.type == "work" }.map { it.name }
                    _etcTags.value = postTags.filter { it.type == "etc" }.map { it.name }
                }
            }
        }
    }

    fun getTagString(type: String, position: Int): String {
        return when (type) {
            "store" -> {
                storeTags.value?.get(position) ?: ""
            }
            "work" -> {
                workTags.value?.get(position) ?: ""
            }
            "etc" -> {
                etcTags.value?.get(position) ?: ""
            }
            else -> ""
        }
    }

    fun addTag(type: String, tag: String) {
        when (type) {
            "store" -> {
                _storeTags.value = storeTags.value?.plus(tag) ?: listOf(tag)
            }
            "work" -> {
                _workTags.value = workTags.value?.plus(tag) ?: listOf(tag)
            }
            "etc" -> {
                _etcTags.value = etcTags.value?.plus(tag) ?: listOf(tag)
            }
        }
    }

    fun editTag(type: String, tag: String, position: Int) {
        when (type) {
            "store" -> {
                _storeTags.value = storeTags.value?.mapIndexed { index, s ->
                    if (index == position) tag else s
                }
            }
            "work" -> {
                _workTags.value = workTags.value?.mapIndexed { index, s ->
                    if (index == position) tag else s
                }
            }
            "etc" -> {
                _etcTags.value = etcTags.value?.mapIndexed { index, s ->
                    if (index == position) tag else s
                }
            }
        }
    }

    fun deleteTag(type: String, position: Int) {
        when (type) {
            "store" -> {
                _storeTags.value = storeTags.value?.filterIndexed { index, _ -> index != position }
            }
            "work" -> {
                _workTags.value = workTags.value?.filterIndexed { index, _ -> index != position }
            }
            "etc" -> {
                _etcTags.value = etcTags.value?.filterIndexed { index, _ -> index != position }
            }
        }
    }
}