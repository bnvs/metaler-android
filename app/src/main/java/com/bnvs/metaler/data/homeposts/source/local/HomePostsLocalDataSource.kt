package com.bnvs.metaler.data.homeposts.source.local

import com.bnvs.metaler.data.homeposts.model.HomePosts

interface HomePostsLocalDataSource {

    fun getHomePosts(
        onSuccess: (response: HomePosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}