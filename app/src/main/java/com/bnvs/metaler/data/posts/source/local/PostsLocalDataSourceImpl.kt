package com.bnvs.metaler.data.posts.source.local

import android.content.Context
import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.model.PostsWithContentRequest
import com.bnvs.metaler.data.posts.model.PostsWithTagRequest
import com.bnvs.metaler.util.constants.LOCAL_POSTS_DATA

class PostsLocalDataSourceImpl(context: Context) : PostsLocalDataSource {
    private val sharedPreferences =
        context.getSharedPreferences(LOCAL_POSTS_DATA, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getPosts(
        request: PostsRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun getPostsWithSearchTypeContent(
        request: PostsWithContentRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }

    override fun getPostsWithSearchTypeTag(
        request: PostsWithTagRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }
}