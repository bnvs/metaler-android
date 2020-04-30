package com.bnvs.metaler.ui.detail.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.item_content_img_rv.view.*

class ContentImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(imageUrl: String) {
        Glide.with(itemView)
            .load(imageUrl)
            .transform(CenterCrop())
            .into(itemView.contentImg)
    }

}