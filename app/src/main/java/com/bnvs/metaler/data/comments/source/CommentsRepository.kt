package com.bnvs.metaler.data.comments.source

import android.content.Context
import com.bnvs.metaler.data.comments.*
import com.bnvs.metaler.data.comments.source.remote.CommentsRemoteDataSource

class CommentsRepository(context: Context) : CommentsDataSource {

    private val commentsRemoteDataSource = CommentsRemoteDataSource

    override fun getComments(
        postId: Int,
        onSuccess: (response: Comments) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        commentsRemoteDataSource.getComments(postId, onSuccess, onFailure)
    }

    override fun addComment(
        postId: Int,
        request: AddEditCommentRequest,
        onSuccess: (response: AddCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        commentsRemoteDataSource.addComment(postId, request, onSuccess, onFailure)
    }

    override fun editComment(
        commentId: Int,
        request: AddEditCommentRequest,
        onSuccess: (response: EditCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        commentsRemoteDataSource.editComment(commentId, request, onSuccess, onFailure)
    }

    override fun deleteComment(
        commentId: Int,
        onSuccess: (response: DeleteCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        commentsRemoteDataSource.deleteComment(commentId, onSuccess, onFailure)
    }
}