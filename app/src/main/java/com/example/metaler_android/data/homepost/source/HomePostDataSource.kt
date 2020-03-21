package com.example.metaler_android.data.homepost.source

import com.example.metaler_android.data.homepost.HomePosts

interface HomePostDataSource {

    interface LoadHomePostsCallback {
        fun onHomePostsLoaded(homePosts: HomePosts)
        fun onDataNotAvailable()
    }

    fun getHomePosts(callback: LoadHomePostsCallback)

}