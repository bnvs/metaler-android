package com.bnvs.metaler.view.myposts

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.view.myposts.recyclerview.MyPostsAdapter

@BindingAdapter("setMyPostItems")
fun setMyPostItems(view: RecyclerView, items: List<MyPost>?) {
    (view.adapter as? MyPostsAdapter)?.run {
        items?.let { replaceAll(it) }
        notifyDataSetChanged()
    }
}
