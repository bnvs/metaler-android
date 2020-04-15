package com.bnvs.metaler.data.homeposts.source

import android.content.Context
import com.bnvs.metaler.data.homeposts.HomePosts
import com.bnvs.metaler.data.homeposts.source.remote.HomePostsRemoteDataSource

class HomePostsRepository(context: Context) : HomePostsDataSource {

    private val homePostsRemoteDataSource = HomePostsRemoteDataSource

    override fun getHomePosts(
        onSuccess: (response: HomePosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        homePostsRemoteDataSource.getHomePosts(onSuccess, onFailure)
    }
}