package com.bnvs.metaler.data.homeposts.source

import com.bnvs.metaler.data.homeposts.model.HomePosts

interface HomePostsDataSource {

    fun getHomePosts(
        onSuccess: (response: HomePosts) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}