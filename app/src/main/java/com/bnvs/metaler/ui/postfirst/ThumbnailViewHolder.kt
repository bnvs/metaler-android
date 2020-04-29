package com.bnvs.metaler.ui.postfirst

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_thumbnail_rv.view.*

class ThumbnailViewHolder(
    itemView: View,
    private val itemClick: (adapterPosition: Int) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnLongClickListener {
            itemClick(adapterPosition)
            true
        }
    }

    fun bind(attachUrl: String) {
        Glide.with(itemView)
            .load(attachUrl)
            .into(itemView.thumbnailImg)
    }

}