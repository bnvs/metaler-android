package com.bnvs.metaler.data.posts.source

import com.bnvs.metaler.data.posts.Posts

interface PostDataSource {

    interface LoadPostsCallback {
        fun onPostsLoaded(posts: Posts)
        fun onDataNotAvailable()
    }

    fun getPosts(callback: LoadPostsCallback)

}