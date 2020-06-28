package com.bnvs.metaler.view.bookmarks

import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bnvs.metaler.data.bookmarks.model.BookmarksRequest
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.postsrv.BasePostsRvViewModel

class ViewModelBookmarks(
    private val bookmarksRepository: BookmarksRepository
) : BasePostsRvViewModel<Bookmark>() {

    private val _categoryType = MutableLiveData<String>().apply { value = "materials" }
    val categoryType: MutableLiveData<String> = _categoryType

    init {
        loadPosts()
    }

    override fun loadPosts() {
        bookmarksRepository.getMyBookmarks(
            getBookmarksRequest(),
            onSuccess = { response ->
                setItemLoadingView(false)
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
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    private fun getBookmarksRequest(): BookmarksRequest {
        page++
        return BookmarksRequest(categoryType.value ?: "materials", page, limit)
    }

    override fun loadMorePosts() {
        _hasNextPage.value = false
        setItemLoadingView(true)
        val handler = android.os.Handler()
        handler.postDelayed({ loadPosts() }, 1000)
    }

    fun changeCategoryType(categoryType: String) {
        if (_categoryType.value != categoryType) {
            _categoryType.value = categoryType
            resetPage()
            loadPosts()
        }
    }

    override fun deleteBookmark(bookmarkId: Int, position: Int) {
        bookmarksRepository.deleteBookmark(
            DeleteBookmarkRequest(bookmarkId),
            onSuccess = {
                posts.value?.filterIndexed { index, _ ->
                    index != position
                }.let {
                    _posts.value = it
                    if (posts.value.isNullOrEmpty()) {
                        _errorVisibility.value = true
                    }
                    _errorToastMessage.setMessage("북마크에서 삭제되었습니다")
                }
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }
}