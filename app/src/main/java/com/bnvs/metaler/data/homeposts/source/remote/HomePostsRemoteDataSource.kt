package com.bnvs.metaler.data.homeposts.source.remote

import com.bnvs.metaler.data.homeposts.source.HomePostsDataSource
import com.bnvs.metaler.network.RetrofitClient

object HomePostsRemoteDataSource : HomePostsDataSource{

    private val retrofitClient = RetrofitClient.client

    override fun getHomePosts(callback: HomePostsDataSource.LoadHomePostsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}