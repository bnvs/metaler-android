package com.bnvs.metaler.data.posts

data class PostsRequest(
    val category_type: Int,
    val page: Int,
    val limit: Int,
    val search_type: String?,
    val search_word: String?
)