package com.bnvs.metaler.data.comments.source

import com.bnvs.metaler.data.comments.model.*

interface CommentsDataSource {

    fun getComments(
        postId: Int,
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
        onSuccess: (response: EditCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun deleteComment(
        commentId: Int,
        onSuccess: (response: DeleteCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}