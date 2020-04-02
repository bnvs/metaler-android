package com.bnvs.metaler.data.posts

data class PostsRequest(
    val access_token: String,
    val category: List<PostsCategory>
)