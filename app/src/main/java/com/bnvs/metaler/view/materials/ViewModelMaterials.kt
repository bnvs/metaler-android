package com.bnvs.metaler.view.materials

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.postsrv.BasePostsRvViewModel
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE
import com.bnvs.metaler.util.constants.POST_REQUEST_TYPE
import com.bnvs.metaler.util.constants.POST_REQUEST_WITH_SEARCH_TYPE_TAG

class ViewModelMaterials(
    private val postRepository: PostsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val categoriesRepository: CategoriesRepository
) : BasePostsRvViewModel() {

    private val TAG = "ViewModel Materials"

    // 카테고리 리스트 + 선택한 카테고리 아이디(category_id)
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories
    private val _selectedCategoryId = MutableLiveData<Int>()
    val selectedCategoryId: LiveData<Int> = _selectedCategoryId

    // 게시글 목록 요청 request 용 데이터
    override val categoryId = selectedCategoryId

    init {
        loadCategories()
    }

    override fun refresh() {
        super.refresh()
        loadPosts()
    }

    private fun loadCategories() {
        categoriesRepository.getCategories(
            onSuccess = { response ->
                _categories.value = response
                response.first { it.type == "total" }.id.let {
                    _selectedCategoryId.value = it
                    setSearchViewCategoryType(it)
                }
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

    override fun setSearchViewCategoryType(categoryId: Int) {
        categoriesRepository.saveSearchViewCategoryTypeCache(categoryId)
    }

    fun changeSelectedCategory(categoryId: Int) {
        if (_selectedCategoryId.value != categoryId) {
            _selectedCategoryId.value = categoryId
            resetPage()
            loadPosts()
        }
    }

    override fun loadPosts() {
        when (postRequestType) {
            POST_REQUEST_TYPE -> loadPostsNormal()
            POST_REQUEST_WITH_SEARCH_TYPE_TAG -> loadPostsWithSearchTypeTag()
        }
        Log.d(TAG, "게시물 목록 가져오기 - postRequestType : $postRequestType")
    }

    override fun loadMorePosts() {
        _hasNextPage.value = false
        setItemLoadingView(true)
        val handler = android.os.Handler()
        handler.postDelayed({ loadPosts() }, 1000)
    }

    override fun loadPostsNormal() {
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

    override fun loadPostsWithSearchTypeTag() {
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

    override fun addBookmark(postId: Int, position: Int) {
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

    override fun deleteBookmark(bookmarkId: Int, position: Int) {
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

}