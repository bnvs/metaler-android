package com.bnvs.metaler.data.homeposts.source

import android.content.Context
import com.bnvs.metaler.data.homeposts.source.local.HomePostsLocalDataSource
import com.bnvs.metaler.data.homeposts.source.remote.HomePostsRemoteDataSource

class HomePostsRepository(context: Context) : HomePostsDataSource{

    private val homePostsLocalDataSource = HomePostsLocalDataSource(context)
    private val homePostsRemoteDataSource = HomePostsRemoteDataSource

    override fun getHomePosts(callback: HomePostsDataSource.LoadHomePostsCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}