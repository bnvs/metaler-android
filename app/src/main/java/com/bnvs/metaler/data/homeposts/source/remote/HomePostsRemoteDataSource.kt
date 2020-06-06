package com.bnvs.metaler.data.homeposts.source.remote

import com.bnvs.metaler.data.homeposts.model.HomePosts

interface HomePostsRemoteDataSource {

    fun getHomePosts(
        onSuccess: (response: HomePosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}