package com.bnvs.metaler.bookmarks

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.util.TapBarContract

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