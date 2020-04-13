package com.bnvs.metaler.data.myposts

data class MyPost(
    val id: Int,
    val title: String,
    val content: String,
    val user_id: Int,
    val date: String,
    val profile_nickname: String,
    val liked: Int,
    val disliked: Int,
    val is_bookmark: Boolean
)