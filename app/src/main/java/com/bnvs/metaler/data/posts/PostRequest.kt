package com.bnvs.metaler.data.posts

data class PostRequest(
    val access_token: String,
    val category: List<PostCategory>
)