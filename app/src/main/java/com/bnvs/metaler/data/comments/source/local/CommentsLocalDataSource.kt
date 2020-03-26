package com.bnvs.metaler.data.comments.source.local

import android.content.Context
import com.bnvs.metaler.data.comments.source.CommentsDataSource

class CommentsLocalDataSource(context: Context) : CommentsDataSource {

    private val sharedPreferences = context.getSharedPreferences("comments", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getComments(callback: CommentsDataSource.LoadCommentsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}