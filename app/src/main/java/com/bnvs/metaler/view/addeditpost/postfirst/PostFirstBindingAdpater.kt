package com.bnvs.metaler.view.addeditpost.postfirst

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.postdetails.model.AttachImage
import com.bnvs.metaler.view.addeditpost.postfirst.thumbnails.ThumbnailAdapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_thumbnail_rv.view.*
import java.text.NumberFormat

@BindingAdapter("postLoadingViewVisibility")
fun postLoadingViewVisibility(view: View, visibility: Boolean) {
    if (visibility) {
        view.bringToFront()
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("categoryInputVisibility")
fun categoryInputVisibility(view: View, categoryType: String?) {
    when (categoryType) {
        "materials" -> view.visibility = View.VISIBLE
        "manufacture" -> view.visibility = View.GONE
    }
}

@BindingAdapter("price")
fun setPrice(view: TextView, price: Int?) {
    if (price != null) {
        view.text = NumberFormat.getInstance().format(price)
    } else {
        view.text = null
    }
}

@BindingAdapter("onPriceTypeChecked")
fun onPriceTypeChecked(view: TextView, b: Boolean) {
    if (b) {
        view.setBackgroundResource(R.drawable.purple_rounding_border)
        view.setTextColor(ContextCompat.getColor(view.context, R.color.colorPurple))
    } else {
        view.setBackgroundResource(R.drawable.lightgrey_rounding_border)
        view.setTextColor(ContextCompat.getColor(view.context, R.color.colorLightGrey))
    }
}

@BindingAdapter("thumbnailListVisibility")
fun thumbnailListVisibility(view: Group, listSize: Int) {
    if (listSize > 0) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setThumbnailItems")
fun setThumbnailItems(view: RecyclerView, items: List<AttachImage>?) {
    (view.adapter as? ThumbnailAdapter)?.run {
        items?.let { replaceAll(it) }
        notifyDataSetChanged()
    }
}

@BindingAdapter("attachImage")
fun attachImage(view: View, imageUrl: String) {
    Glide.with(view.context)
        .load(imageUrl)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                view.thumbnailLoading.visibility = View.GONE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                view.thumbnailLoading.visibility = View.GONE
                return false
            }
        })
        .centerCrop()
        .override(300, 300)
        .error(R.drawable.ic_broken_image_black_24dp)
        .into(view.thumbnailImg)
}