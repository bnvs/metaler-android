package com.bnvs.metaler.view.manufactures

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.postsrv.BasePostsRvViewModel
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE
import com.bnvs.metaler.util.constants.POST_REQUEST_TYPE
import com.bnvs.metaler.util.constants.POST_REQUEST_WITH_SEARCH_TYPE_TAG

class ViewModelManufactures(
    private val postRepository: PostsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val categoriesRepository: CategoriesRepository
) : BasePostsRvViewModel() {

    private val TAG = "ViewModel Manufactures"

    // 가공탭 기본 카테고리 아이디 : 10
    private val _categoryId = MutableLiveData<Int>().apply { value = 0 }
    override val categoryId: LiveData<Int> = _categoryId

    init {
        setManufacturesCategoryId()
    }

    override fun refresh() {
        super.refresh()
        loadPosts()
    }

    private fun setManufacturesCategoryId() {
        categoriesRepository.getCategories(
            onSuccess = { response ->
                _categoryId.value = response.first { it.type == "manufacture" }.id
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