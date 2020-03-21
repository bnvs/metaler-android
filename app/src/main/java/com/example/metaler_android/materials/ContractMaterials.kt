package com.example.metaler_android.materials

import android.content.Context
import com.example.metaler_android.BasePresenter
import com.example.metaler_android.BaseView

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractMaterials {
    interface View : BaseView<Presenter>{
        fun showCategories()

        fun showPosts()

        fun showPostDetailUi()

        fun showSearchUi()

        fun showSearchTags()

        fun setTapBarListener(context: Context)
    }

    interface Presenter : BasePresenter {
        fun loadCategories()

        fun loadPosts()

        fun openPostDetail()

        fun openSearch()

        fun searchTag()

        fun setTapBar(context: Context)
    }
}