package com.example.metaler_android.data.source

import com.example.metaler_android.data.HomePosts
import com.example.metaler_android.data.Materials
import com.example.metaler_android.data.PostDetails
import com.example.metaler_android.data.Posts

interface DataSource {
    interface LoadHomePostsCallback {
        fun onHomePostsLoaded(homePosts: HomePosts)
        fun onDataNotAvailable()
    }

    interface LoadMaterialsCallback {
        fun onMaterialsLoaded(materials: Materials)
        fun onDataNotAvailable()
    }

    interface LoadPostsCallback {
        fun onPostsLoaded(posts: Posts)
        fun onDataNotAvailable()
    }

    interface LoadPostDetailsCallback {
        fun onPostDetailsLoaded(postDetails: PostDetails)
        fun onDataNotAvailable()
    }
}