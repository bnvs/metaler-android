package com.example.metaler_android.data.post

data class PostCategory(
    val category_type: String,
    val title: String,
    val price: Int,
    val price_type: Int,
    val attach_ids: List<Int>,
    val tags: List<String>,
    val content: String
)