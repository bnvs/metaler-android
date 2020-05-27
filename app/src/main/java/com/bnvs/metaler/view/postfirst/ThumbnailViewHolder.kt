package com.bnvs.metaler.view.postfirst

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            itemView.thumbnailLoading.indeterminateDrawable.colorFilter =
                BlendModeColorFilter(R.color.colorLightPurple, BlendMode.SRC_ATOP)
        } else {
            itemView.thumbnailLoading.indeterminateDrawable.setColorFilter(
                Color.GRAY,
                PorterDuff.Mode.MULTIPLY
            )
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
            .centerCrop()
            .override(300, 300)
            .error(R.drawable.ic_broken_image_black_24dp)
            .into(itemView.thumbnailImg)
    }

}