package com.bnvs.metaler.data.myposts.model

import com.bnvs.metaler.data.addeditpost.model.PostTag

data class MyPost(
    val id: Int,
    val title: String,
    val is_updated: Boolean,
    val user_id: Int,
    val date: String,
    val profile_nickname: String,
    val liked: Int,
    val disliked: Int,
    val is_bookmark: Boolean,
    val bookmark_id: Int,
    val thumbnail: String,
    val tags: List<PostTag>,
    val category_name: String,
    val comment_count: Int
)