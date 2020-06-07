package com.bnvs.metaler.data.comments.source.repository

import com.bnvs.metaler.data.comments.model.AddCommentResponse
import com.bnvs.metaler.data.comments.model.AddEditCommentRequest
import com.bnvs.metaler.data.comments.model.Comments
import com.bnvs.metaler.data.comments.model.CommentsRequest
import com.bnvs.metaler.data.comments.source.local.CommentsLocalDataSource
import com.bnvs.metaler.data.comments.source.remote.CommentsRemoteDataSource

class CommentsRepositoryImpl(
    private val commentsLocalDataSource: CommentsLocalDataSource,
    private val commentsRemoteDataSource: CommentsRemoteDataSource
) : CommentsRepository {

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
        postId: Int,
        commentId: Int,
        request: AddEditCommentRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        commentsRemoteDataSource.editComment(postId, commentId, request, onSuccess, onFailure)
    }

    override fun deleteComment(
        postId: Int,
        commentId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        commentsRemoteDataSource.deleteComment(postId, commentId, onSuccess, onFailure)
    }

    override fun saveIsCommentModified(isCommentModified: Boolean) {
        commentsLocalDataSource.saveIsCommentModified(isCommentModified)
    }

    override fun isCommentModified(): Boolean {
        return commentsLocalDataSource.isCommentModified()
    }
}