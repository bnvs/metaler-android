package com.example.metaler_android.data.postdetail.source.remote

import com.example.metaler_android.data.postdetail.source.PostDetailDataSource
import com.example.metaler_android.network.RetrofitClient

object PostDetailsRemoteDataSource : PostDetailDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getPostDetails(callback: PostDetailDataSource.LoadPostDetailsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}