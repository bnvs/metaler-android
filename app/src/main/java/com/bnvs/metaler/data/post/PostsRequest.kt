package com.bnvs.metaler.data.post

data class PostsRequest(
    val access_token: String,
    val category: List<PostsCategory>
)