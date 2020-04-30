package com.bnvs.metaler.ui.detail.viewholder

import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_content_img_rv.view.*

class ContentImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(imageUrl: String) {
        Glide.with(itemView)
            .load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemView.contentImgLoading.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    itemView.contentImgLoading.visibility = View.GONE
                    return false
                }
            })
            .transform(CenterCrop())
            .error(R.drawable.ic_broken_image_black_24dp)
            .into(itemView.contentImg)
    }

}