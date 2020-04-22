package com.bnvs.metaler.data.postdetails.model

data class PostDetails(
    val post_id: Int,
    val category_id: Int,
    val user_id: Int,
    val title: String,
    val content: String,
    val price: Int,
    val price_type: String,
    val nickname: String,
    val date: String,
    val liked: Int,
    val disliked: Int,
    val attach_ids: List<Int>,
    val attach_urls: List<String>,
    val tags: List<String>,
    val is_bookmark: Boolean,
    val comment_count: Int,
    val is_rating: Int
)