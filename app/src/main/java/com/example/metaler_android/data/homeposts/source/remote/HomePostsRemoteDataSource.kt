package com.example.metaler_android.data.homeposts.source.remote

import com.example.metaler_android.data.homeposts.source.HomePostsDataSource
import com.example.metaler_android.network.RetrofitClient

object HomePostsRemoteDataSource : HomePostsDataSource{

    private val retrofitClient = RetrofitClient.client

    override fun getHomePosts(callback: HomePostsDataSource.LoadHomePostsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}