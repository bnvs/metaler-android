package com.example.metaler_android.manufactures

import android.content.Context
import com.example.metaler_android.BasePresenter
import com.example.metaler_android.BaseView

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractManufactures {
    interface View : BaseView<Presenter> {
        fun showPosts()

        fun showPostDetailUi()

        fun showSearchUi()

        fun showSearchTags()

        fun clearSearchTagBar()

        fun deleteSearchTag()

        fun setTapBarListener(context: Context)
    }

    interface Presenter : BasePresenter {
        fun loadPosts()

        fun openPostDetail(clickedPostId: Int)

        fun openSearch()

        fun AddsearchTag()

        fun clearSearchTagBar()

        fun deleteSearchTag()

        fun setTapBar(context: Context)
    }
}