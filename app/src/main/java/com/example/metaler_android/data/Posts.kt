package com.example.metaler_android.data

data class Posts(
    val is_next: Boolean,
    val post_count: Int,
    val posts: List<PostsItem>
)

data class PostsItem(
    val post_id: Int,
    val title: String,
    val nickname: Int,
    val date: String,
    val tags: List<String>,
    val image_url: String,
    val like: Int,
    val dis_like: Int,
    val is_bookmark: Boolean
)