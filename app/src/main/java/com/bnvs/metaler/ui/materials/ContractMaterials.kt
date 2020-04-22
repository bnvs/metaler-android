package com.bnvs.metaler.ui.materials

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.util.TapBarContract

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractMaterials {
    interface View : BaseView<Presenter>, TapBarContract.View{
        fun showCategories()

        fun showPosts(posts: List<Post>)

        fun showMorePosts(posts: List<Post>)

        fun setRVScrollListener()

        fun showPostDetailUi()

        fun showSearchUi()

        fun showSearchTags()

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadCategories()

        fun requestPosts(): PostsRequest

        fun loadPosts(postsRequest: PostsRequest)

        fun loadMorePosts(postsRequest: PostsRequest)

        fun refreshPosts()

        fun openPostDetail(postId: Int)

        fun openWritePost()

        fun addBookmark(postId: Int)

        fun deleteBookmark(postId: Int)

        fun openSearch()

        fun addSearchTag()

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }
}