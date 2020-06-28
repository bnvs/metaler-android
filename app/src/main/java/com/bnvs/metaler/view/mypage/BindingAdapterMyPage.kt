package com.bnvs.metaler.view.mypage

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bnvs.metaler.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

@BindingAdapter("myPageProfileImage")
fun myPageProfileImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .transform(CircleCrop())
        .error(R.drawable.ic_profile_x3)
        .into(view)
}