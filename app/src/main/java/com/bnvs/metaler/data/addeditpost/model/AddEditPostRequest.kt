package com.bnvs.metaler.data.addeditpost.model

import java.io.Serializable

data class AddEditPostRequest(
    var category_id: Int?,
    var title: String?,
    var content: String?,
    var price: Int?,
    var price_type: String?,
    var attach_ids: MutableList<Int>,
    var tags: MutableList<PostTag>
) : Serializable