package com.example.metaler_android.data.source

import com.example.metaler_android.data.HomePosts
import com.example.metaler_android.data.Posts

interface DataSource {
    interface LoadHomePostsCallback {
        fun onHomePostsLoaded(homePosts: HomePosts)
        fun onDataNotAvailable()
    }
}