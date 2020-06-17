package com.bnvs.metaler.util.posts.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.util.posts.adapter.TagsAdapter

@BindingAdapter("tagsRvVisibility")
fun tagsRvVisibility(view: RecyclerView, visibility: Boolean) {
    if (visibility) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setTagItems")
fun setTagItems(view: RecyclerView, items: List<String>?) {
    (view.adapter as? TagsAdapter)?.run {
        items?.let { replaceAll(it) }
        notifyDataSetChanged()
    }
}