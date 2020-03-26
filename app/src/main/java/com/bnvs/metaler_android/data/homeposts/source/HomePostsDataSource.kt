package com.bnvs.metaler_android.data.homeposts.source

import com.bnvs.metaler_android.data.homeposts.HomePosts

interface HomePostsDataSource {

    interface LoadHomePostsCallback {
        fun onHomePostsLoaded(homePosts: HomePosts)
        fun onDataNotAvailable()
    }

    fun getHomePosts(callback: LoadHomePostsCallback)

}