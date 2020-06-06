package com.bnvs.metaler.data.comments.source.local

import android.content.Context
import com.bnvs.metaler.data.comments.model.AddCommentResponse
import com.bnvs.metaler.data.comments.model.AddEditCommentRequest
import com.bnvs.metaler.data.comments.model.Comments
import com.bnvs.metaler.data.comments.model.CommentsRequest
import com.bnvs.metaler.util.constants.LOCAL_COMMENTS_DATA

class CommentsLocalDataSourceImpl(context: Context) : CommentsLocalDataSource {

    private val shared =
        context.getSharedPreferences(LOCAL_COMMENTS_DATA, Context.MODE_PRIVATE)

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
        shared.edit().apply {
            putBoolean("isCommentModified", isCommentModified)
            apply()
        }
    }

    override fun isCommentModified(): Boolean {
        return shared.getBoolean("isCommentModified", false)
    }
}