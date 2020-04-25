package com.bnvs.metaler.ui.manufactures

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.util.TapBarContract

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractManufactures {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showPosts(posts: List<Post>)

        fun showMorePosts(posts: List<Post>)

        fun setRVScrollListener()

        fun onRefresh()

        fun showPostDetailUi()

        fun showSearchUi()

        fun showSearchTags()

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {

        fun requestPosts(): PostsRequest

        fun requestAddBookmark(postId: Int): AddBookmarkRequest

        fun loadPosts(postsRequest: PostsRequest)

        fun loadMorePosts(postsRequest: PostsRequest)

        fun refreshPosts()

        fun openPostDetail(postId: Int)

        fun addBookmark(postId: Int)

        fun deleteBookmark(postId: Int)

        fun openSearch()

        fun addSearchTag()

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }
}