package com.bnvs.metaler.ui.modifycomment

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.comments.model.Comment

interface ContractModifyComment {
    interface View : BaseView<Presenter> {
        fun showComment(comment: Comment)
        fun showModifiedComment(comment: String)
        fun showModifyCommentSuccessToast()
        fun setCommentToModify(comment: String)
        fun hideSoftInput()
        fun showSoftInput()
        fun showErrorToast(errorMessage: String)
    }

    interface Presenter : BasePresenter {
        val content: String
        fun modifyComment(comment: String)
    }

}