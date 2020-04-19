package com.bnvs.metaler.data.myposts.model

data class MyPosts(
    val count: Int,
    val is_next: Boolean,
    val posts: List<MyPost>
)