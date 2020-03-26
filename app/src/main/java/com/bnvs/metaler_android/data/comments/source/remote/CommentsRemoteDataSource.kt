package com.bnvs.metaler_android.data.comments.source.remote

import com.bnvs.metaler_android.data.comments.source.CommentsDataSource
import com.bnvs.metaler_android.network.RetrofitClient

object CommentsRemoteDataSource : CommentsDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getComments(callback: CommentsDataSource.LoadCommentsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}