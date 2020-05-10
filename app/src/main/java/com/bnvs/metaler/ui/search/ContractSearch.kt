package com.bnvs.metaler.ui.search

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsWithContentRequest
import com.bnvs.metaler.util.TapBarContract

interface ContractSearch {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showPosts(posts: List<Post>)

        fun showMorePosts(posts: List<Post>)

        fun removeLoadingView()

        fun setRVScrollListener()

        fun onRefresh()

        fun showRefreshPosts(posts: List<Post>)

        fun hideError404()

        fun showError404()
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadSearchPosts(postsWithContentRequest: PostsWithContentRequest)

        fun requestSearchPosts(categoryType: Int): PostsWithContentRequest

        fun resetPageNum()

        fun getCategoryId(): Int

        fun openPostDetail(postId: Int)
    }
}