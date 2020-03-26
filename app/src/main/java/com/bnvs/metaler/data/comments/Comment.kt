package com.bnvs.metaler.data.comments

data class Comment(
    val comment_id: Int,
    val profile_nickname: String,
    val date: String,
    val content: String,
    val profile_url: String
)