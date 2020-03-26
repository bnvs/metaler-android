package com.bnvs.metaler.detail

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractDetail {
    interface View : BaseView<Presenter> {
        fun showPostDetail()

        fun showComments()

        fun showMenuDialog()

        fun refreshRatingButtons()

    }

    interface Presenter : BasePresenter {
        fun loadPostDetail()

        fun loadComments()

        fun addBookmark(postId: Int)

        fun deleteBookmark(postId: Int)

        fun openMenu()

        fun deletePost(postId: Int)

        fun modifyPost(postId: Int)

        fun ratePost(postId: Int)
    }
}
