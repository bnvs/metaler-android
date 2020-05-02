package com.bnvs.metaler.ui.detail.listener

import com.bnvs.metaler.data.comments.model.Comment

interface CommentMenuListener {
    fun onClickMenuButton(comment: Comment, commentIndex: Int)
}