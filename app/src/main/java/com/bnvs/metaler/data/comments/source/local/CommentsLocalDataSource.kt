package com.bnvs.metaler.data.comments.source.local

import android.content.Context
import com.bnvs.metaler.data.comments.model.AddCommentResponse
import com.bnvs.metaler.data.comments.model.AddEditCommentRequest
import com.bnvs.metaler.data.comments.model.Comments
import com.bnvs.metaler.data.comments.model.CommentsRequest
import com.bnvs.metaler.data.comments.source.CommentsDataSource

class CommentsLocalDataSource(context: Context) : CommentsDataSource {

    private val sharedPreferences = context.getSharedPreferences("comments", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getComments(
        postId: Int,
        request: CommentsRequest,
        onSuccess: (response: Comments) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun addComment(
        postId: Int,
        request: AddEditCommentRequest,
        onSuccess: (response: AddCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun editComment(
        postId: Int,
        commentId: Int,
        request: AddEditCommentRequest,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun deleteComment(
        postId: Int,
        commentId: Int,
        onSuccess: () -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun saveIsCommentModified(isCommentModified: Boolean) {
        editor.putBoolean("isCommentModified", isCommentModified)
        editor.commit()
    }

    override fun isCommentModified(): Boolean {
        return sharedPreferences.getBoolean("isCommentModified", false)
    }
}