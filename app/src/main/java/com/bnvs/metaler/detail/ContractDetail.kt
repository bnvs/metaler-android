package com.bnvs.metaler.detail

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractDetail {
    interface View : BaseView<Presenter> {
        fun showPost()

        fun showComments()

        fun showMenuDialog()

    }

    interface Presenter : BasePresenter {
        fun loadPost()

        fun loadComments()

        fun addBookmark(postId: Int)

        fun deleteBookmark(postId: Int)

        fun openMenu()

        fun deletePost()

        fun modifyPost()
    }
}
