package com.bnvs.metaler_android.data.post

data class PostRequest(
    val access_token: String,
    val category: List<PostCategory>
)