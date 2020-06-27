package com.bnvs.metaler.view.addeditpost.postsecond

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.addeditpost.model.AddEditPostLocalCache
import com.bnvs.metaler.data.addeditpost.model.AddEditPostRequest
import com.bnvs.metaler.data.addeditpost.model.PostTag
import com.bnvs.metaler.data.addeditpost.source.repository.AddEditPostRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.BaseViewModel
import com.bnvs.metaler.util.constants.MODE_ADD_POST
import com.bnvs.metaler.util.constants.MODE_EDIT_POST

class ViewModelPostSecond(
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
    private val _finishAddEditPostActivity = MutableLiveData<String>().apply { value = "" }
    val finishAddEditPostActivity: LiveData<String> = _finishAddEditPostActivity

    // 다이얼로그 띄우기 관련 데이터
    private val _openTagSelectionDialog =
        MutableLiveData<Map<String, Any>>().apply { value = mapOf() }
    val openTagSelectionDialog: LiveData<Map<String, Any>> = _openTagSelectionDialog
    private val _openAddTagDialog =
        MutableLiveData<Map<String, Any>>().apply { value = mapOf() }
    val openAddTagDialog: LiveData<Map<String, Any>> = _openAddTagDialog
    private val _openEditTagDialog =
        MutableLiveData<Map<String, Any>>().apply { value = mapOf() }
    val openEditTagDialog: LiveData<Map<String, Any>> = _openEditTagDialog
    private val _openDeleteTagDialog =
        MutableLiveData<Map<String, Any>>().apply { value = mapOf() }
    val openDeleteTagDialog: LiveData<Map<String, Any>> = _openDeleteTagDialog


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

    private fun savePostSecondCacheData() {
        addEditPostCache.value?.copy(
            tags = getIntegratedTagList()
        )?.let {
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
        } ?: _errorToastMessage.setMessage("게시글 작성 데이터에 문제가 있습니다")
    }

    private fun getIntegratedTagList(): List<PostTag> {
        return mutableListOf<PostTag>().apply {
            for (storeTag in storeTags.value ?: listOf()) {
                this.add(PostTag("store", storeTag))
            }
            if (categoryType.value == "manufacture") {
                for (workTag in workTags.value ?: listOf()) {
                    this.add(PostTag("work", workTag))
                }
            }
            for (etcTag in etcTags.value ?: listOf()) {
                this.add(PostTag("etc", etcTag))
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

    private fun String.removeEmptySpace(): String {
        return this.replace("\\s".toRegex(), "")
    }

    fun addTag(type: String, tagInput: String) {
        val tag = tagInput.removeEmptySpace()
        when (type) {
            "store" -> {
                _storeTags.value.let {
                    if (it?.contains(tag) == true) {
                        _errorToastMessage.setMessage("동일한 내용의 태그가 존재합니다")
                    } else {
                        _storeTags.value = storeTags.value?.plus(tag) ?: listOf(tag)
                    }
                }
            }
            "work" -> {
                _workTags.value.let {
                    if (it?.contains(tag) == true) {
                        _errorToastMessage.setMessage("동일한 내용의 태그가 존재합니다")
                    } else {
                        _workTags.value = workTags.value?.plus(tag) ?: listOf(tag)
                    }
                }
            }
            "etc" -> {
                _etcTags.value.let {
                    if (it?.contains(tag) == true) {
                        _errorToastMessage.setMessage("동일한 내용의 태그가 존재합니다")
                    } else {
                        _etcTags.value = etcTags.value?.plus(tag) ?: listOf(tag)
                    }
                }
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

    fun backToPostSecondActivity() {
        savePostSecondCacheData()
        _backToPostFirstActivity.enable()
    }

    fun completeAddEditPostActivity() {
        if (!checkStoreTagInput()) {
            _errorDialogMessage.setMessage("가게 이름 태그를 입력해 주세요")
            return
        }
        if (categoryType.value == "manufacture" && !checkWorkTagInput()) {
            _errorDialogMessage.setMessage("작업 종류 태그를 입력해 주세요")
            return
        }
        savePostSecondCacheData()
        submitAddEditPostRequest()
    }

    private fun checkStoreTagInput(): Boolean {
        return !storeTags.value.isNullOrEmpty()
    }

    private fun checkWorkTagInput(): Boolean {
        return !workTags.value.isNullOrEmpty()
    }

    private fun submitAddEditPostRequest() {
        AddEditPostRequest(
            addEditPostCache.value?.category_id ?: return,
            addEditPostCache.value?.title ?: return,
            addEditPostCache.value?.content ?: return,
            addEditPostCache.value?.price ?: return,
            addEditPostCache.value?.price_type ?: return,
            addEditPostCache.value?.attach_ids?.map { it.id } ?: listOf(),
            getIntegratedTagList()
        ).let {
            Log.d("게시글 작성 완료", it.toString())
            when (mode) {
                MODE_ADD_POST -> {
                    addEditPostRepository.addPost(
                        it,
                        onSuccess = { finishAddEditPostActivity() },
                        onFailure = { e ->
                            _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
                            finishAddEditPostActivity
                        },
                        handleError = { e -> _errorCode.setErrorCode(e) }
                    )
                }
                MODE_EDIT_POST -> {
                    addEditPostRepository.editPost(
                        postId!!, it,
                        onSuccess = { finishAddEditPostActivity() },
                        onFailure = { e ->
                            _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
                            finishAddEditPostActivity
                        },
                        handleError = { e -> _errorCode.setErrorCode(e) }
                    )
                }
                else -> {
                    _errorToastMessage.setMessage("게시글 작성에 문제가 생겼습니다")
                    return
                }
            }
        }
    }

    private fun finishAddEditPostActivity() {
        _finishAddEditPostActivity.setMessage(categoryType.value ?: "")
    }

    fun openTagSelectionDialog(type: String, position: Int) {
        _openTagSelectionDialog.value = mapOf("type" to type, "position" to position)
    }

    fun openAddTagDialog(type: String) {
        when (type) {
            "store" -> {
                if (_storeTags.value?.size ?: 0 > 9) {
                    _errorToastMessage.setMessage("가게 이름 태그는 최대 10개까지 입력 가능합니다")
                } else {
                    _openAddTagDialog.value = mapOf("type" to type)
                }
            }
            "work" -> {
                if (_workTags.value?.size ?: 0 > 9) {
                    _errorToastMessage.setMessage("작업 종류 태그는 최대 10개까지 입력 가능합니다")
                } else {
                    _openAddTagDialog.value = mapOf("type" to type)
                }
            }
            "etc" -> {
                if (_etcTags.value?.size ?: 0 > 19) {
                    _errorToastMessage.setMessage("기타 태그는 최대 20개까지 입력 가능합니다")
                } else {
                    _openAddTagDialog.value = mapOf("type" to type)
                }
            }
        }
    }

    fun openEditTagDialog(type: String, position: Int) {
        _openEditTagDialog.value = mapOf("type" to type, "position" to position)
    }

    fun openDeleteTagDialog(type: String, position: Int) {
        _openDeleteTagDialog.value = mapOf("type" to type, "position" to position)
    }
}