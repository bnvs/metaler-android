package com.bnvs.metaler.ui.postfirst

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_thumbnail_rv.view.*

class ThumbnailViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    private var attachUrl = ""

    fun bind(attachUrl: String) {
        this.attachUrl = attachUrl
        Glide.with(itemView)
            .load(attachUrl)
            .into(itemView.thumbnailImg)
    }

}