package com.bnvs.metaler.data.homeposts.source.repository

import com.bnvs.metaler.data.homeposts.model.HomePosts

interface HomePostsRepository {

    fun getHomePosts(
        onSuccess: (response: HomePosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}