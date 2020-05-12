package com.bnvs.metaler.ui.search

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsWithContentRequest
import com.bnvs.metaler.util.TapBarContract

interface ContractSearch {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun inputSearchWord()

        fun showSearchPosts(posts: List<Post>)

        fun showSearchTotalNum(totalNum: Int)

        fun showMoreSearchPosts(posts: List<Post>)

        fun removeLoadingView()

        fun setRVScrollListener()

        fun setSearchButtons()

        fun onRefresh()

        fun showRefreshPosts(posts: List<Post>)

        fun postAdapterAddBookmark(position: Int, bookmarkId: Int)

        fun hideError404()

        fun showError404()
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadSearchPosts(postsWithContentRequest: PostsWithContentRequest)

        fun loadMoreSearchPosts(postsWithContentRequest: PostsWithContentRequest)

        fun requestSearchPosts(categoryId: Int, searchWord: String): PostsWithContentRequest

        fun resetPageNum()

        fun getCategoryId(): Int

        fun openPostDetail(postId: Int)

        fun requestAddBookmark(postId: Int): AddBookmarkRequest

        fun addBookmark(postId: Int, bookmarkId: Int, position: Int)

        fun deleteBookmark(bookmarkId: Int)
    }
}