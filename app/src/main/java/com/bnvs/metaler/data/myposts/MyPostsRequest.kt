package com.bnvs.metaler.data.myposts

data class MyPostsRequest(
    val page: Int,
    val limit: Int,
    val type: String
)