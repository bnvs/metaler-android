package com.bnvs.metaler.ui.postfirst

import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemView.thumbnailLoading.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemView.thumbnailLoading.visibility = View.GONE
                    return false
                }
            })
            .error(R.drawable.ic_broken_image_black_24dp)
            .into(itemView.thumbnailImg)
    }

}