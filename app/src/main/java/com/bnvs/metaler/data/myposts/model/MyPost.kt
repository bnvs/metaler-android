package com.bnvs.metaler.data.myposts.model

import com.bnvs.metaler.data.addeditpost.model.PostTag
import com.bnvs.metaler.data.postdetails.model.AttachImage

data class MyPost(
    val id: Int,
    val title: String,
    val content: String,
    val user_id: Int,
    val date: String,
    val profile_nickname: String,
    val liked: Int,
    val disliked: Int,
    val thumbnail: String,
    val tags: List<PostTag>,
    val is_bookmark: Boolean
)