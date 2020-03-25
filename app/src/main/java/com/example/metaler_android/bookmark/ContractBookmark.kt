package com.example.metaler_android.bookmark

import com.example.metaler_android.BasePresenter
import com.example.metaler_android.BaseView
import com.example.metaler_android.util.TapBarContract

interface ContractBookmark {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showPostDetailUi()

        fun showMaterialsList()

        fun showManufacturesList()

        fun showBookmarkDeleteDialog()

    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadMaterialsPost()

        fun loadManufacturePost()

        fun openMaterialsList()

        fun openManufacturesList()

        fun openPostDetail(postId: Int)

        fun openBookmarkDelete()

        fun deleteBookmark()

    }
}