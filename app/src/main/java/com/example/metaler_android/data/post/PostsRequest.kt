package com.example.metaler_android.data.post

data class PostsRequest(
    val access_token: String,
    val category: List<PostsCategory>
)