package com.bnvs.metaler.data.comments.model

data class Comment(
    val comment_id: Int,
    val user_id: Int,
    val content: String,
    val date: String,
    val profile_nickname: String,
    val profile_url: String
)