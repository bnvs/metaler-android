package com.bnvs.metaler.data.myposts.source.repository

import com.bnvs.metaler.data.myposts.model.MyPosts
import com.bnvs.metaler.data.myposts.model.MyPostsRequest

interface MyPostsRepository {

    fun getMyPosts(
        request: MyPostsRequest,
        onSuccess: (response: MyPosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}