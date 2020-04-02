package com.bnvs.metaler.data.posts

data class PostsResponse(
    val count: Int,
    val is_next: Boolean,
    val posts: List<Post>
)