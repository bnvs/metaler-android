package com.bnvs.metaler_android.data.postdetails.source.remote

import com.bnvs.metaler_android.data.postdetails.source.PostDetailsDataSource
import com.bnvs.metaler_android.network.RetrofitClient

object PostDetailsRemoteDataSource : PostDetailsDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getPostDetails(callback: PostDetailsDataSource.LoadPostDetailsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}