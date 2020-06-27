package com.bnvs.metaler.util.base.postsrvadvanced

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsWithContentRequest
import com.bnvs.metaler.data.posts.model.PostsWithTagRequest
import com.bnvs.metaler.util.base.postsrv.BasePostsRvViewModel
import com.bnvs.metaler.util.constants.POST_REQUEST_TYPE
import com.bnvs.metaler.util.constants.POST_REQUEST_WITH_SEARCH_TYPE_TAG
import com.bnvs.metaler.util.constants.POST_SEARCH_TYPE_TAG

abstract class BasePostsRvAdvancedViewModel<ITEM> : BasePostsRvViewModel<ITEM>() {

    protected val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    // 글 작성 1단계 , 글 검색 페이지
    private val _openPostFirstActivity = MutableLiveData<Boolean>().apply { value = false }
    val openPostFirstActivity: LiveData<Boolean> = _openPostFirstActivity
    private val _openSearchActivity = MutableLiveData<Boolean>().apply { value = false }
    val openSearchActivity: LiveData<Boolean> = _openSearchActivity

    // 게시글 목록 조회 타입 :
    protected var postRequestType = POST_REQUEST_TYPE
    private val _tagsRvVisibility = MutableLiveData<Boolean>().apply { value = false }
    val tagsRvVisibility: LiveData<Boolean> = _tagsRvVisibility

    // 태그/검색어 입력 TextView 텍스트
    val editTextInput = MutableLiveData<String>()

    // 태그 추천 리스트
    protected val _tagSuggestions = MutableLiveData<List<String>>()
    val tagSuggestions: LiveData<List<String>> = _tagSuggestions

    // 검색어
    private var contentSearchWord: String? = null

    // 게시글 목록 요청 request 용 데이터
    protected abstract val categoryId: LiveData<Int>
    protected var searchType = POST_SEARCH_TYPE_TAG
    private val _searchWord = MutableLiveData<List<String>>()
    val searchWord: LiveData<List<String>> = _searchWord

    open fun refresh() {
        _isLoading.value = true
        _hasNextPage.value = false
        resetPage()
        clearEditTextInput()
        clearSearchWord()
        setTagsRvVisibility()
        setPostRequestType()
    }

    open fun refreshForOnResume() {
        _isLoading.value = true
        _hasNextPage.value = false
        resetPage()
    }

    protected abstract fun setCategoryTypeCache(categoryId: Int)

    protected abstract fun loadPostsNormal()
    protected abstract fun loadPostsWithSearchTypeTag()

    abstract fun addBookmark(postId: Int, position: Int)

    abstract fun getTagSuggestions(input: String)

    private fun String.removeEmptySpace(): String {
        return this.replace("\\s".toRegex(), "")
    }

    fun addSearchWord() {
        val tag = (editTextInput.value)?.removeEmptySpace()
        if (tag.isNullOrBlank()) {
            _errorToastMessage.setMessage("검색할 태그를 입력해주세요")
        } else {
            searchWord.value.let {
                if (it?.contains(tag) == true) {
                    _errorToastMessage.setMessage("동일한 내용의 태그가 존재합니다")
                    clearEditTextInput()
                } else {
                    _searchWord.value = it?.plus(tag) ?: listOf(tag)
                    setTagsRvVisibility()
                    setPostRequestType()
                    clearEditTextInput()
                    resetPage()
                    loadPosts()
                }
            }
        }
    }

    fun removeSearchWord(position: Int) {
        searchWord.value?.filterIndexed { index, _ ->
            index != position
        }.let {
            _searchWord.value = it ?: listOf()
            setTagsRvVisibility()
            setPostRequestType()
            resetPage()
            loadPosts()
        }
    }

    fun searchWithContent() {
        val input = editTextInput.value
        if (input.isNullOrBlank()) {
            _errorToastMessage.setMessage("검색어를 입력해주세요")
        } else {
            if (input != contentSearchWord) {
                contentSearchWord = input
                resetPage()
                loadPosts()
            }
        }
    }


    protected fun getPostsRequest(): PostsRequest {
        page++
        return PostsRequest(categoryId.value ?: 0, page, limit)
    }

    protected fun getPostsWithTagRequest(): PostsWithTagRequest {
        page++
        return PostsWithTagRequest(
            categoryId.value ?: 0,
            page,
            limit,
            searchType,
            searchWord.value?.map { "\"$it\"" } ?: listOf()
        )
    }

    protected fun getPostsWithContentRequest(): PostsWithContentRequest {
        page++
        return PostsWithContentRequest(
            categoryId.value ?: 0,
            page,
            limit,
            searchType,
            contentSearchWord ?: ""
        )
    }


    fun clearEditTextInput() {
        editTextInput.value = ""
    }

    private fun clearSearchWord() {
        _searchWord.value = listOf()
    }

    private fun setTagsRvVisibility() {
        val isSearchWordExist = searchWord.value?.size ?: 0 > 0
        _tagsRvVisibility.value = isSearchWordExist
    }

    private fun setPostRequestType() {
        val isTagSearch = tagsRvVisibility.value ?: false
        postRequestType = if (isTagSearch) {
            POST_REQUEST_WITH_SEARCH_TYPE_TAG
        } else {
            POST_REQUEST_TYPE
        }
    }

    fun startPostFirstActivity() {
        _openPostFirstActivity.enable()
    }

    fun startSearchActivity() {
        _openSearchActivity.enable()
    }
}