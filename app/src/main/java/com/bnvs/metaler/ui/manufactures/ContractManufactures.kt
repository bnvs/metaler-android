package com.bnvs.metaler.ui.manufactures

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsWithTagRequest
import com.bnvs.metaler.util.TapBarContract

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractManufactures {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showPosts(posts: List<Post>)

        fun showMorePosts(posts: List<Post>)

        fun removeLoadingView()

        fun setRVScrollListener()

        fun onRefresh()

        fun postAdapterAddBookmark(position: Int, bookmarkId: Int)

        fun showRefreshPosts(posts: List<Post>)

        fun hideError404()

        fun showError404()

        fun showPostDetailUi()

        fun showSearchUi()

        fun showSearchTags()

        fun clearSearchTagBar()

    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {

        fun requestPosts(): PostsRequest

        fun requestAddSearchTag(categoryId: Int, searchType: String, searchWord: List<String>): PostsWithTagRequest

        fun requestAddBookmark(postId: Int): AddBookmarkRequest

        fun loadPosts(postsRequest: PostsRequest)

        fun loadMorePosts(postsRequest: PostsRequest)

        fun loadSearchTagPosts(postsWithTagRequest: PostsWithTagRequest)

        fun loadMoreSearchTagPosts(postsWithTagRequest: PostsWithTagRequest)

        fun refreshPosts(postsRequest: PostsRequest)

        fun refreshTagSearchPosts(postsWithTagRequest: PostsWithTagRequest)

        fun resetPageNum()

        fun getCategoryId(): Int

        fun openPostDetail(postId: Int)

        fun addBookmark(postId: Int, bookmarkId: Int, position: Int)

        fun deleteBookmark(bookmarkId: Int)

        fun openSearch()

        fun addSearchTag()

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }
}