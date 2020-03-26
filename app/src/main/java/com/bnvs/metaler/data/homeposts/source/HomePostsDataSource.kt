package com.bnvs.metaler.data.homeposts.source

import com.bnvs.metaler.data.homeposts.HomePosts

interface HomePostsDataSource {

    interface LoadHomePostsCallback {
        fun onHomePostsLoaded(homePosts: HomePosts)
        fun onDataNotAvailable()
    }

    fun getHomePosts(callback: LoadHomePostsCallback)

}