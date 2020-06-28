package com.bnvs.metaler.data.myposts.source.repository

import com.bnvs.metaler.data.myposts.model.MyPosts
import com.bnvs.metaler.data.myposts.model.MyPostsRequest
import com.bnvs.metaler.data.myposts.source.local.MyPostsLocalDataSource
import com.bnvs.metaler.data.myposts.source.remote.MyPostsRemoteDataSource

class MyPostsRepositoryImpl(
    private val myPostsLocalDataSource: MyPostsLocalDataSource,
    private val myPostsRemoteDataSource: MyPostsRemoteDataSource
) : MyPostsRepository {

    override fun getMyPosts(
        request: MyPostsRequest,
        onSuccess: (response: MyPosts) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        myPostsRemoteDataSource.getMyPosts(request, onSuccess, onFailure, handleError)
    }
}