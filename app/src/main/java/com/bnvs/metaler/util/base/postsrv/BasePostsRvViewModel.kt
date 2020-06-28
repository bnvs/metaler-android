package com.bnvs.metaler.util.base.postsrv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.util.base.posts.BasePostsViewModel

abstract class BasePostsRvViewModel<ITEM> : BasePostsViewModel() {

    // 게시물 리스트
    protected val _posts = MutableLiveData<List<ITEM?>>()
    val posts: LiveData<List<ITEM?>> = _posts
    protected val _hasNextPage = MutableLiveData<Boolean>().apply { false }
    val hasNextPage: LiveData<Boolean> = _hasNextPage

    // 게시글 목록 요청 request 용 데이터
    protected var page = 0
    protected val limit = 10

    protected abstract fun loadPosts()
    abstract fun loadMorePosts()

    open fun deleteBookmark(bookmarkId: Int, position: Int) {}

    protected fun resetPage() {
        page = 0
        _errorVisibility.value = false
        _posts.value = listOf()
    }

    protected fun setItemLoadingView(b: Boolean) {
        val list = posts.value
        val nullPost: ITEM? = null
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
}