package com.bnvs.metaler_android.data.comments.source

import android.content.Context
import com.bnvs.metaler_android.data.comments.source.local.CommentsLocalDataSource
import com.bnvs.metaler_android.data.comments.source.remote.CommentsRemoteDataSource

class CommentsRepository(context: Context) : CommentsDataSource{

    private val commentsLocalDataSource = CommentsLocalDataSource(context)
    private val commentsRemoteDataSource = CommentsRemoteDataSource

    override fun getComments(callback: CommentsDataSource.LoadCommentsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}