package com.bnvs.metaler.data.addeditpost.model

data class AddEditPostRequest(
    var category_id: Int?,
    var title: String?,
    var content: String?,
    var price: Int?,
    var price_type: String?,
    var attach_ids: List<Int>?,
    var tags: List<AddEditPostTag>?
)