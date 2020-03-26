package com.bnvs.metaler.data.comments.source.remote

import com.bnvs.metaler.data.comments.source.CommentsDataSource
import com.bnvs.metaler.network.RetrofitClient

object CommentsRemoteDataSource : CommentsDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getComments(callback: CommentsDataSource.LoadCommentsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}