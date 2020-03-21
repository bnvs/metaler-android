package com.example.metaler_android.data.homepost.source.remote

import com.example.metaler_android.data.homepost.source.HomePostDataSource
import com.example.metaler_android.network.RetrofitClient

object HomePostsRemoteDataSource : HomePostDataSource{

    private val retrofitClient = RetrofitClient.client

    override fun getHomePosts(callback: HomePostDataSource.LoadHomePostsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}