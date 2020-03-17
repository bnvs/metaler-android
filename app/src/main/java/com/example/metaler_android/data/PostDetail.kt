package com.example.metaler_android.data

data class PostDetail(
    val Post: List<Post>
)

data class Post(
    val post_id: Int,
    val title: String,
    val nickname: Int,
    val date: String,
    val tags: List<String>,
    val image_url: String,
    val like: Int,
    val dis_like: Int,
    val is_bookmark: Boolean,
    val content: String,
    val attachs: List<String>,
    // val tags: List<String>,
    val comment_count: Int,
    val price: Int,
    val user_rating: String
)