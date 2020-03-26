package com.bnvs.metaler.data.post

data class PostRequest(
    val access_token: String,
    val category: List<PostCategory>
)