package com.bnvs.metaler.data.myposts.source.remote

import com.bnvs.metaler.data.myposts.model.MyPosts
import com.bnvs.metaler.data.myposts.model.MyPostsRequest

interface MyPostsRemoteDataSource {

    fun getMyPosts(
        request: MyPostsRequest,
        onSuccess: (response: MyPosts) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )
}