package com.bnvs.metaler.data.addeditpost.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddEditPostRequest(
    var category_id: Int?,
    var title: String?,
    var content: String?,
    var price: Int?,
    var price_type: String?,
    var attach_ids: MutableList<Int>,
    var tags: MutableList<PostTag>
) : Parcelable