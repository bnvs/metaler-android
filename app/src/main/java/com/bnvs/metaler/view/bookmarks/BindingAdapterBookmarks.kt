package com.bnvs.metaler.view.bookmarks

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bnvs.metaler.util.setOnSingleClickListener
import com.bnvs.metaler.view.bookmarks.recyclerview.BookmarkClickListener
import com.bnvs.metaler.view.bookmarks.recyclerview.BookmarksAdapter

@BindingAdapter(value = ["position", "bookmarkId", "listener"])
fun bookmarkClickListener(
    view: View,
    position: Int,
    bookmarkId: Int,
    listener: BookmarkClickListener
) {
    view.setOnSingleClickListener(
        View.OnClickListener {
            listener.deleteBookmarkButtonClick(bookmarkId, position)
        }
    )
}

@BindingAdapter("setBookmarkItems")
fun setBookmarkItems(view: RecyclerView, items: List<Bookmark>?) {
    (view.adapter as? BookmarksAdapter)?.run {
        items?.let { replaceAll(it) }
        notifyDataSetChanged()
    }
}
