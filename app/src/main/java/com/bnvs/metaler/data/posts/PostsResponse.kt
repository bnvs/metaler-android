package com.bnvs.metaler.data.posts

data class PostsResponse(
    val is_next: Boolean,
    val post_count: Int,
    val posts: List<Post>
)