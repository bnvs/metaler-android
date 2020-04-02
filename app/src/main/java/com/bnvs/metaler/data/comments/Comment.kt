package com.bnvs.metaler.data.comments

data class Comment(
    val id: Int,
    val user_id: Int,
    val profile_nickname: String,
    val profile_image_url: String,
    val content: String,
    val created_at: String,
    val updated_at: String
)