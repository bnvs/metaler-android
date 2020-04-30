package com.bnvs.metaler.data.postdetails.model

import com.bnvs.metaler.data.addeditpost.model.PostTag

data class PostDetails(
    val post_id: Int,
    val category_id: Int,
    val user_id: Int,
    val title: String,
    val content: String,
    val price: Int,
    val price_type: String,
    val nickname: String,
    val profile_url: String,
    val date: String,
    var liked: Int,
    var disliked: Int,
    val attachs: List<AttachImage>,
    val tags: List<PostTag>,
    var is_bookmark: Boolean,
    var comment_count: Int,
    var rating: Int
)