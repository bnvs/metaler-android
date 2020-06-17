package com.bnvs.metaler.util.base.postsrv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsWithTagRequest
import com.bnvs.metaler.util.base.posts.BasePostsViewModel
import com.bnvs.metaler.util.constants.POST_REQUEST_TYPE
import com.bnvs.metaler.util.constants.POST_REQUEST_WITH_SEARCH_TYPE_TAG
import com.bnvs.metaler.util.constants.POST_SEARCH_TYPE_TAG

abstract class BasePostsRvViewModel : BasePostsViewModel() {

    protected val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    // 글 작성 1단계 , 글 검색 페이지
    private val _openPostFirstActivity = MutableLiveData<Boolean>().apply { value = false }
    val openPostFirstActivity: LiveData<Boolean> = _openPostFirstActivity
    private val _openSearchActivity = MutableLiveData<Boolean>().apply { value = false }
    val openSearchActivity: LiveData<Boolean> = _openSearchActivity

    // 게시물 리스트
    protected val _posts = MutableLiveData<List<Post?>>()
    val posts: LiveData<List<Post?>> = _posts
    protected val _hasNextPage = MutableLiveData<Boolean>().apply { false }
    val hasNextPage: LiveData<Boolean> = _hasNextPage

    // 게시글 목록 조회 타입 :
    protected var postRequestType = POST_REQUEST_TYPE
    private val _tagsRvVisibility = MutableLiveData<Boolean>().apply { value = false }
    val tagsRvVisibility: LiveData<Boolean> = _tagsRvVisibility

    // 태그 입력 TextView 텍스트
    val tagInput = MutableLiveData<String>()

    // 게시글 목록 요청 request 용 데이터
    protected abstract val categoryId: LiveData<Int>
    protected var page = 0
    protected val limit = 10
    private val searchType = POST_SEARCH_TYPE_TAG
    private val _searchWord = MutableLiveData<List<String>>()
    val searchWord: LiveData<List<String>> = _searchWord


    open fun refresh() {
        _isLoading.value = true
        _hasNextPage.value = false
        clearTagInput()
        clearSearchWord()
        setTagsRvVisibility()
        setPostRequestType()
    }

    protected abstract fun setSearchViewCategoryType(categoryId: Int)

    protected abstract fun loadPosts()
    abstract fun loadMorePosts()

    protected abstract fun loadPostsNormal()
    protected abstract fun loadPostsWithSearchTypeTag()

    abstract fun addBookmark(postId: Int, position: Int)
    abstract fun deleteBookmark(bookmarkId: Int, position: Int)

    protected fun setItemLoadingView(b: Boolean) {
        val list = posts.value
        val nullPost: Post? = null
        if (!list.isNullOrEmpty()) {
            if (b) {
                _posts.value = list.plus(nullPost)
            } else {
                if (list[list.size - 1] == null) {
                    _posts.value = list.filterIndexed { index, _ ->
                        index < list.size - 1
                    }
                }
            }
        }
    }

    fun addSearchWord() {
        val tag = tagInput.value
        if (tag.isNullOrBlank()) {
            _errorToastMessage.apply {
                value = "검색할 태그를 입력해주세요"
                value = clearStringValue()
            }
        } else {
            searchWord.value.let {
                if (it?.contains(tag) == true) {
                    _errorToastMessage.apply {
                        value = "동일한 내용의 태그가 존재합니다"
                        value = clearStringValue()
                    }
                    clearTagInput()
                } else {
                    _searchWord.value = it?.plus(tag) ?: listOf(tag)
                    setTagsRvVisibility()
                    setPostRequestType()
                    clearTagInput()
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


    fun clearTagInput() {
        tagInput.value = ""
    }

    private fun clearSearchWord() {
        _searchWord.value = listOf()
    }

    protected fun resetPage() {
        page = 0
        _errorVisibility.value = false
        _posts.value = listOf()
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
        _openPostFirstActivity.apply {
            value = true
            value = false
        }
    }

    fun startSearchActivity() {
        _openSearchActivity.apply {
            value = true
            value = false
        }
    }
}