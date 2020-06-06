package com.bnvs.metaler.data.homeposts.source.repository

import com.bnvs.metaler.data.homeposts.model.HomePosts
import com.bnvs.metaler.data.homeposts.source.local.HomePostsLocalDataSource
import com.bnvs.metaler.data.homeposts.source.remote.HomePostsRemoteDataSource

class HomePostsRepositoryImpl(
    private val homePostsLocalDataSource: HomePostsLocalDataSource,
    private val homePostsRemoteDataSource: HomePostsRemoteDataSource
) : HomePostsRepository {

    override fun getHomePosts(
        onSuccess: (response: HomePosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        homePostsRemoteDataSource.getHomePosts(onSuccess, onFailure)
    }
}