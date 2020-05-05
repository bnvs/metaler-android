package com.bnvs.metaler.data.posts.model

data class PostsRequest(
    val category_id: Int,
    val page: Int,
    val limit: Int
)