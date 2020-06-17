package com.bnvs.metaler.view.materials

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsWithTagRequest
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.BaseViewModel
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE
import com.bnvs.metaler.util.constants.POST_REQUEST_TYPE
import com.bnvs.metaler.util.constants.POST_REQUEST_WITH_SEARCH_TYPE_TAG
import com.bnvs.metaler.util.constants.POST_SEARCH_TYPE_TAG

class ViewModelMaterials(
    private val postRepository: PostsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel() {

    private val TAG = "ViewModel Materials"

    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    // 상세 페이지 + post_id
    private val _openDetailActivity = MutableLiveData<Boolean>().apply { value = false }
    val openDetailActivity: LiveData<Boolean> = _openDetailActivity
    private val _postId = MutableLiveData<Int>()
    val postId: LiveData<Int> = _postId

    // 글 작성 1단계 , 글 검색 페이지
    private val _openPostFirstActivity = MutableLiveData<Boolean>().apply { value = false }
    val openPostFirstActivity: LiveData<Boolean> = _openPostFirstActivity
    private val _openSearchActivity = MutableLiveData<Boolean>().apply { value = false }
    val openSearchActivity: LiveData<Boolean> = _openSearchActivity

    // 에러 화면
    private val _errorVisibility = MutableLiveData<Boolean>().apply { value = false }
    val errorVisibility: LiveData<Boolean> = _errorVisibility

    // 게시물 리스트
    private val _posts = MutableLiveData<List<Post?>>()
    val posts: LiveData<List<Post?>> = _posts
    private val _hasNextPage = MutableLiveData<Boolean>().apply { false }
    val hasNextPage: LiveData<Boolean> = _hasNextPage

    // 카테고리 리스트 + 선택한 카테고리 아이디(category_id)
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories
    private val _selectedCategoryId = MutableLiveData<Int>()
    val selectedCategoryId: LiveData<Int> = _selectedCategoryId

    // 게시글 목록 조회 타입 :
    private var postRequestType = POST_REQUEST_TYPE
    private val _tagsRvVisibility = MutableLiveData<Boolean>().apply { value = false }
    val tagsRvVisibility: LiveData<Boolean> = _tagsRvVisibility

    // 태그 입력 TextView 텍스트
    val tagInput = MutableLiveData<String>()

    // 게시글 목록 요청 request 용 데이터
    private var categoryId = selectedCategoryId
    private var page = 0
    private val limit = 10
    private val searchType = POST_SEARCH_TYPE_TAG
    private val _searchWord = MutableLiveData<List<String>>()
    val searchWord: LiveData<List<String>> = _searchWord

    init {
        loadCategories()
    }

    fun refresh() {
        _isLoading.value = true
        _hasNextPage.value = false
        clearTagInput()
        clearSearchWord()
        setTagsRvVisibility()
        setPostRequestType()
        loadCategories()
    }

    private fun loadCategories() {
        categoriesRepository.getCategories(
            onSuccess = { response ->
                _categories.value = response
                initSelectedCategory()
                loadPosts()
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

    private fun initSelectedCategory() {
        _selectedCategoryId.value = categories.value?.first {
            it.type == "total"
        }?.id ?: 0.also {
            _errorToastMessage.apply {
                value = "카테고리 에러발생 - 카테고리 total 없음"
                value = clearStringValue()
            }
        }
        Log.d(TAG, "init selected category - 카테고리 아이디 : ${categoryId.value}")
    }

    private fun setItemLoadingView(b: Boolean) {
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

    fun loadMorePosts() {
        _hasNextPage.value = false
        setItemLoadingView(true)
        val handler = android.os.Handler()
        handler.postDelayed({ loadPosts() }, 1000)
    }

    private fun loadPosts() {
        when (postRequestType) {
            POST_REQUEST_TYPE -> loadPostsNormal()
            POST_REQUEST_WITH_SEARCH_TYPE_TAG -> loadPostsWithSearchTypeTag()
        }
        Log.d(TAG, "게시물 목록 가져오기 - postRequestType : $postRequestType")
    }

    private fun loadPostsNormal() {
        postRepository.getPosts(
            getPostsRequest(),
            onSuccess = { response ->
                setItemLoadingView(false)
                _isLoading.value = false
                if (response.posts.isNullOrEmpty()) {
                    if (posts.value.isNullOrEmpty()) {
                        _errorVisibility.value = true
                    }
                } else {
                    _errorVisibility.value = false
                    _hasNextPage.value = response.is_next
                    _posts.value = posts.value?.plus(response.posts) ?: response.posts
                }
            },
            onFailure = { e ->
                setItemLoadingView(false)
                _isLoading.value = false
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

    private fun loadPostsWithSearchTypeTag() {
        postRepository.getPostsWithSearchTypeTag(
            getPostsWithTagRequest(),
            onSuccess = { response ->
                setItemLoadingView(false)
                _isLoading.value = false
                if (response.posts.isNullOrEmpty()) {
                    if (posts.value.isNullOrEmpty()) {
                        _errorVisibility.value = true
                    }
                } else {
                    _errorVisibility.value = false
                    _hasNextPage.value = response.is_next
                    _posts.value = posts.value?.plus(response.posts) ?: response.posts
                }
            },
            onFailure = { e ->
                setItemLoadingView(false)
                _isLoading.value = false
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

    fun clearTagInput() {
        tagInput.value = ""
    }

    private fun clearSearchWord() {
        _searchWord.value = listOf()
    }

    private fun resetPage() {
        page = 0
        _errorVisibility.value = false
        _posts.value = listOf()
    }

    private fun getPostsRequest(): PostsRequest {
        page++
        return PostsRequest(categoryId.value ?: 0, page, limit)
    }

    private fun getPostsWithTagRequest(): PostsWithTagRequest {
        page++
        return PostsWithTagRequest(
            categoryId.value ?: 0,
            page,
            limit,
            searchType,
            searchWord.value?.map { "\"$it\"" } ?: listOf()
        )
    }

    fun changeSelectedCategory(categoryId: Int) {
        if (_selectedCategoryId.value != categoryId) {
            _selectedCategoryId.value = categoryId
            resetPage()
            loadPosts()
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

    fun addBookmark(postId: Int, position: Int) {
        bookmarksRepository.addBookmark(
            AddBookmarkRequest(postId),
            onSuccess = { response ->
                posts.value?.mapIndexed { index, post ->
                    if (index == position) {
                        post?.copy(
                            bookmark_id = response.bookmark_id,
                            is_bookmark = true
                        )
                    } else {
                        post
                    }
                }.let {
                    _posts.value = it
                    _errorToastMessage.apply {
                        value = "북마크되었습니다"
                        value = clearStringValue()
                    }
                }
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

    fun deleteBookmark(bookmarkId: Int, position: Int) {
        bookmarksRepository.deleteBookmark(
            DeleteBookmarkRequest(bookmarkId),
            onSuccess = {
                posts.value?.mapIndexed { index, post ->
                    if (index == position) {
                        post?.copy(
                            bookmark_id = 0,
                            is_bookmark = false
                        )
                    } else {
                        post
                    }
                }.let {
                    _posts.value = it
                    _errorToastMessage.apply {
                        value = "북마크에서 삭제되었습니다"
                        value = clearStringValue()
                    }
                }
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

    fun openPostDetail(postId: Int) {
        _postId.value = postId
        startDetailActivity()
    }

    private fun startDetailActivity() {
        _openDetailActivity.apply {
            value = true
            value = false
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