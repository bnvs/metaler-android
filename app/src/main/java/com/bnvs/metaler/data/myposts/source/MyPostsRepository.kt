package com.bnvs.metaler.data.myposts.source

import com.bnvs.metaler.data.myposts.MyPosts
import com.bnvs.metaler.data.myposts.MyPostsRequest
import com.bnvs.metaler.data.myposts.source.remote.MyPostsRemoteDataSource

class MyPostsRepository : MyPostsDataSource {

    private val myPostsRemoteDataSource = MyPostsRemoteDataSource

    override fun getMyPosts(
        request: MyPostsRequest,
        onSuccess: (response: MyPosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        myPostsRemoteDataSource.getMyPosts(request, onSuccess, onFailure)
    }
}