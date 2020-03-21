package com.example.metaler_android.data.homepost.source

import android.content.Context
import com.example.metaler_android.data.homepost.source.local.HomePostsLocalDataSource
import com.example.metaler_android.data.homepost.source.remote.HomePostsRemoteDataSource

class HomePostRepository(context: Context) : HomePostDataSource{

    private val homePostsLocalDataSource = HomePostsLocalDataSource(context)
    private val homePostsRemoteDataSource = HomePostsRemoteDataSource

    override fun getHomePosts(callback: HomePostDataSource.LoadHomePostsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}