package com.bnvs.metaler.view.myposts

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.util.setOnSingleClickListener
import com.bnvs.metaler.view.myposts.recyclerview.MyPostClickListener
import com.bnvs.metaler.view.myposts.recyclerview.MyPostsAdapter

@BindingAdapter(value = ["position", "postId", "listener"])
fun myPostClickListener(
    view: View,
    position: Int,
    postId: Int,
    listener: MyPostClickListener
) {
    view.setOnSingleClickListener(
        View.OnClickListener {
            listener.onMoreButtonClick(postId, position)
        }
    )
}

@BindingAdapter("setMyPostItems")
fun setMyPostItems(view: RecyclerView, items: List<MyPost>?) {
    (view.adapter as? MyPostsAdapter)?.run {
        items?.let { replaceAll(it) }
        notifyDataSetChanged()
    }
}
