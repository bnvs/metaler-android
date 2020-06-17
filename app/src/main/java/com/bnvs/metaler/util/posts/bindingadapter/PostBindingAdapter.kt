package com.bnvs.metaler.util.posts.bindingadapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.addeditpost.model.PostTag
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.util.posts.adapter.PostsAdapter
import com.bnvs.metaler.util.posts.listener.PostClickListener
import com.bnvs.metaler.util.setOnSingleClickListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("postImage")
fun postImage(view: ImageView, imageUrl: String?) {
    if (imageUrl.isNullOrEmpty()) {
        view.visibility = View.INVISIBLE
    } else {
        view.visibility = View.VISIBLE
        Glide.with(view.context)
            .asBitmap()
            .load(imageUrl)
            .error(R.drawable.rounding_img_view)
            .override(500, 500)
            .transform(CenterCrop(), RoundedCorners(24))
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(view)
    }
}

@BindingAdapter("postTags")
fun postTags(view: TextView, tags: List<PostTag>?) {
    if (!tags.isNullOrEmpty()) {
        var tagString = ""
        for (tag in tags) {
            tagString += "#${tag.name} "
        }
        view.text = tagString
    }
}

@BindingAdapter("postBookmarkChecked")
fun postBookmarkChecked(view: CheckBox, isBookmark: Boolean) {
    view.isChecked = isBookmark
}

@BindingAdapter("postErrorVisibility")
fun postErrorVisibility(view: View, visibility: Boolean) {
    if (visibility) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter(value = ["postId", "position", "bookmarkId", "listener"])
fun postBookmarkClickListener(
    view: CheckBox,
    postId: Int,
    position: Int,
    bookmarkId: Int,
    listener: PostClickListener
) {
    view.setOnSingleClickListener(
        View.OnClickListener {
            if (bookmarkId == 0) {
                listener.addBookmarkButtonClick(postId, position)
            } else {
                listener.deleteBookmarkButtonClick(bookmarkId, position)
            }
        }
    )
}

@BindingAdapter("setPostItems")
fun setPostItems(view: RecyclerView, items: List<Post>?) {
    (view.adapter as? PostsAdapter)?.run {
        items?.let { replaceAll(it) }
        notifyDataSetChanged()
    }
}

