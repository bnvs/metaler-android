package com.bnvs.metaler.data.posts.source

import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.model.PostsWithContentRequest
import com.bnvs.metaler.data.posts.model.PostsWithTagRequest

interface PostsDataSource {

    fun getPosts(
        request: PostsRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun getPostsWithSearchTypeContent(
        request: PostsWithContentRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

    fun getPostsWithSearchTypeTag(
        request: PostsWithTagRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}