package com.bnvs.metaler.data.addeditpost.model

import com.bnvs.metaler.data.postdetails.model.AttachImage

data class AddEditPostLocalCache(
    val post_id: Int?,
    val mode: Int,
    val category_type: String,
    val category_id: Int,
    val title: String,
    val price: Int,
    val price_type: String,
    val content: String,
    val attach_ids: List<AttachImage>,
    val tags: List<PostTag>
)