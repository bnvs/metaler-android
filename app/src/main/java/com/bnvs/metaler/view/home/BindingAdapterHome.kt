package com.bnvs.metaler.view.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bnvs.metaler.R
import com.bnvs.metaler.data.addeditpost.model.PostTag
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

@BindingAdapter("homeProfileImage")
fun homeProfileImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .transform(CircleCrop())
        .error(R.drawable.ic_profile_x3)
        .into(view)
}

@BindingAdapter("homeTags")
fun homeTags(view: TextView, tags: List<PostTag>?) {
    if (!tags.isNullOrEmpty()) {
        var tagString = ""
        for (tag in tags) {
            tagString += "#${tag.name} "
        }
        view.text = tagString
    }
}

@BindingAdapter("homeErrorVisibility")
fun homeErrorVisibility(view: View, visibility: Boolean) {
    if (visibility) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}