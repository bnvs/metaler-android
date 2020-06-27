package com.bnvs.metaler.view.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.bookmarks.source.repositroy.BookmarksRepository
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.posts.source.repository.PostsRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.postsrvadvanced.BasePostsRvAdvancedViewModel
import com.bnvs.metaler.util.constants.POST_REQUEST_WITH_SEARCH_TYPE_CONTENT
import com.bnvs.metaler.util.constants.POST_SEARCH_TYPE_CONTENT

class ViewModelSearch(
    private val categoriesRepository: CategoriesRepository,
    private val postsRepository: PostsRepository,
    private val bookmarksRepository: BookmarksRepository
) : BasePostsRvAdvancedViewModel() {

    private val TAG = "ViewModel Manufactures"

    private val _categoryId = MutableLiveData<Int>()
    override val categoryId: LiveData<Int> = _categoryId

    private val _resultCount = MutableLiveData<Int>().apply { value = 0 }
    val resultCount: LiveData<Int> = _resultCount

    private val _finishActivity = MutableLiveData<Boolean>().apply { value = false }
    val finishActivity: LiveData<Boolean> = _finishActivity

    init {
        getSearchViewCategoryType()
        setPostRequestType()
    }

    private fun getSearchViewCategoryType() {
        categoriesRepository.getCategoryTypeCache(
            onSuccess = { categoryType ->
                setCategoryTypeCache(categoryType)
            },
            onFailure = {
                _errorToastMessage.setMessage("검색 타입을 알 수 없습니다")
            }
        )
    }

    override fun setCategoryTypeCache(categoryId: Int) {
        _categoryId.value = categoryId
    }

    private fun setPostRequestType() {
        postRequestType = POST_REQUEST_WITH_SEARCH_TYPE_CONTENT
        searchType = POST_SEARCH_TYPE_CONTENT
    }

    override fun loadPosts() {
        when (postRequestType) {
            POST_REQUEST_WITH_SEARCH_TYPE_CONTENT -> loadPostsNormal()
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
        postsRepository.getPostsWithSearchTypeContent(
            getPostsWithContentRequest(),
            onSuccess = { response ->
                setItemLoadingView(false)
                _isLoading.value = false
                _resultCount.value = response.post_count
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
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    override fun loadPostsWithSearchTypeTag() {}

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
                    _errorToastMessage.setMessage("북마크되었습니다")
                }
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
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
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    fun finishActivity() {
        _finishActivity.enable()
    }

    override fun getTagSuggestions(input: String) {}
}