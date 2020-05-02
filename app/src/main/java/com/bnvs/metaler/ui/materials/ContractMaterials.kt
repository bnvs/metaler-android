package com.bnvs.metaler.ui.materials

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.bookmarks.model.AddBookmarkRequest
import com.bnvs.metaler.data.bookmarks.model.DeleteBookmarkRequest
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.util.TapBarContract

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractMaterials {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showCategories(categories: List<Category>)

        fun showPosts(posts: List<Post>)

        fun showMorePosts(posts: List<Post>)

        fun removeLoadingView()

        fun setRVScrollListener()

        fun onRefresh()

        fun showRefreshPosts(posts: List<Post>)

        fun showPostDetailUi()

        fun showSearchUi()

        fun showSearchTags()

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadCategories()

        fun requestPosts(categoryId: Int): PostsRequest

        fun requestAddBookmark(postId: Int): AddBookmarkRequest

        fun requestDeleteBookmark(postId: Int): DeleteBookmarkRequest

        fun loadPosts(postsRequest: PostsRequest)

        fun loadMorePosts(postsRequest: PostsRequest)

        fun refreshPosts(postsRequest: PostsRequest)

        fun resetPageNum()

        fun getCategoryId(): Int

        fun openPostDetail(postId: Int)

        fun addBookmark(postId: Int)

        fun deleteBookmark(postId: Int)

        fun openSearch()

        fun addSearchTag(searchType: String, searchWord: String)

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }
}