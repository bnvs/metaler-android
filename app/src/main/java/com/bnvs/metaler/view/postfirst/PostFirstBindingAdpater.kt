package com.bnvs.metaler.view.postfirst

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.postdetails.model.AttachImage
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
fun categoryInputVisibility(view: View, categoryType: String) {
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