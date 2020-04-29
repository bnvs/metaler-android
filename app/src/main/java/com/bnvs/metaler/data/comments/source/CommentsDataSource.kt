package com.bnvs.metaler.data.comments.source

import com.bnvs.metaler.data.comments.model.AddCommentResponse
import com.bnvs.metaler.data.comments.model.AddEditCommentRequest
import com.bnvs.metaler.data.comments.model.Comments
import com.bnvs.metaler.data.comments.model.CommentsRequest

interface CommentsDataSource {

    fun getComments(
        postId: Int,
        request: CommentsRequest,
        onSuccess: (response: Comments) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun addComment(
        postId: Int,
        request: AddEditCommentRequest,
        onSuccess: (response: AddCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun editComment(
        commentId: Int,
        request: AddEditCommentRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun deleteComment(
        commentId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}