package com.bnvs.metaler.data.addeditpost.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddEditPostRequest(
    val category_id: Int,
    val title: String,
    val content: String,
    val price: Int,
    val price_type: String,
    val attach_ids: List<Int>,
    val tags: List<PostTag>
) : Parcelable