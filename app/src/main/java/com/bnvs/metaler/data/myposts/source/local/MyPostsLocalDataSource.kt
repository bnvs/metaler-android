package com.bnvs.metaler.data.myposts.source.local

import com.bnvs.metaler.data.myposts.model.MyPosts
import com.bnvs.metaler.data.myposts.model.MyPostsRequest

interface MyPostsLocalDataSource {
    fun getMyPosts(
        request: MyPostsRequest,
        onSuccess: (response: MyPosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}