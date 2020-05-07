package com.bnvs.metaler.data.comments.model

import java.io.Serializable

data class Comment(
    val comment_id: Int,
    val user_id: Int,
    val content: String,
    val date: String,
    val nickname: String,
    val profile_url: String
) : Serializable