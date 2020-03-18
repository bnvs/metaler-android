package com.example.metaler_android.data.source

import com.example.metaler_android.data.*

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

    interface LoadCommentsCallback {
        fun onCommentsLoaded(comments: Comments)
        fun onDataNotAvailable()
    }

    interface LoadJobCallback {
        fun onJobLoaded(job: Job)
        fun onDataNotAvailable()
    }
}