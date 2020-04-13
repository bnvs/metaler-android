package com.bnvs.metaler.data.addeditpost

data class AddEditPostRequest(
    val category_id: Int,
    val title: String,
    val content: String,
    val price: Int,
    val price_type: String,
    val attach_ids: List<Int>,
    val tags: List<AddEditPostTag>
)