package com.example.metaler_android.data.postdetail

data class PostDetail(
    val post_id: Int,
    val title: String,
    val nickname: Int,
    val date: String,
    val like: Int,
    val dis_like: Int,
    val is_bookmark: Boolean,
    val content: String,
    val attach_urls: List<String>,
    val tags: List<String>,
    val comments_count: Int,
    val price: Int,
    val price_type: String,
    val user_rating: String
)