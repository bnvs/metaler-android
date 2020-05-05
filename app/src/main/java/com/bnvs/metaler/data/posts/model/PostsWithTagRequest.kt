package com.bnvs.metaler.data.posts.model

data class PostsWithTagRequest(
    val category_id: Int,
    val page: Int,
    val limit: Int,
    val search_type: String,
    val search_word: List<String>
)