package com.example.metaler_android.manufactures

import android.content.Context
import com.example.metaler_android.BasePresenter
import com.example.metaler_android.BaseView
import com.example.metaler_android.data.post.Post
import com.example.metaler_android.util.TapBarContract

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractManufactures {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showPosts(posts: List<Post>)

        fun showPostDetailUi()

        fun showSearchUi()

        fun showSearchTags()

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadPosts()

        fun openPostDetail(postId: Int)

        fun addBookmark(postId: Int)

        fun deleteBookmark(postId: Int)

        fun openSearch()

        fun addSearchTag()

        fun clearSearchTagBar()

        fun deleteSearchTag()
    }
}