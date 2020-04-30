package com.bnvs.metaler.data.comments.source.repository

import com.bnvs.metaler.data.comments.model.AddCommentResponse
import com.bnvs.metaler.data.comments.model.AddEditCommentRequest
import com.bnvs.metaler.data.comments.model.Comments
import com.bnvs.metaler.data.comments.model.CommentsRequest
import com.bnvs.metaler.data.comments.source.CommentsDataSource
import com.bnvs.metaler.data.comments.source.remote.CommentsRemoteDataSource

class CommentsRepository :
    CommentsDataSource {

    private val commentsRemoteDataSource = CommentsRemoteDataSource

    override fun getComments(
        postId: Int,
        request: CommentsRequest,
        onSuccess: (response: Comments) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        commentsRemoteDataSource.getComments(postId, request, onSuccess, onFailure)
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
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        commentsRemoteDataSource.editComment(commentId, request, onSuccess, onFailure)
    }

    override fun deleteComment(
        commentId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        commentsRemoteDataSource.deleteComment(commentId, onSuccess, onFailure)
    }
}