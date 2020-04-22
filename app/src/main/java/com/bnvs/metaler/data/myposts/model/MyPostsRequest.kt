package com.bnvs.metaler.data.myposts.model

data class MyPostsRequest(
    val page: Int,
    val limit: Int,
    val type: String
)