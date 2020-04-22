package com.bnvs.metaler.data.comments.source.local

import android.content.Context
import com.bnvs.metaler.data.comments.model.*
import com.bnvs.metaler.data.comments.source.CommentsDataSource

class CommentsLocalDataSource(context: Context) : CommentsDataSource {

    private val sharedPreferences = context.getSharedPreferences("comments", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getComments(
        postId: Int,
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editComment(
        commentId: Int,
        request: AddEditCommentRequest,
        onSuccess: (response: EditCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteComment(
        commentId: Int,
        onSuccess: (response: DeleteCommentResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}