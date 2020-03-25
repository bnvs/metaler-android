package com.example.metaler_android.bookmarks

import com.example.metaler_android.BasePresenter
import com.example.metaler_android.BaseView
import com.example.metaler_android.util.TapBarContract

interface ContractBookmarks {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showPostDetailUi(postId: Int)

        fun showMaterialsList()

        fun showManufacturesList()

        fun showBookmarkDeleteDialog(postId: Int)

    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadMaterialsPost()

        fun loadManufacturePost()

        fun openMaterialsList()

        fun openManufacturesList()

        fun openPostDetail(postId: Int)

        fun openBookmarkDelete(postId: Int)

        fun deleteBookmark(postId: Int)

    }
}