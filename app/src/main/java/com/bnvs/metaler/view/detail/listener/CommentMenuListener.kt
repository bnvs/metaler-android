package com.bnvs.metaler.view.detail.listener

import com.bnvs.metaler.data.comments.model.Comment

interface CommentMenuListener {
    fun onClickMenuButton(comment: Comment, commentIndex: Int)
}