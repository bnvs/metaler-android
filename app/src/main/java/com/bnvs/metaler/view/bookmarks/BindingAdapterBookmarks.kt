package com.bnvs.metaler.view.bookmarks

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bnvs.metaler.util.setOnSingleClickListener
import com.bnvs.metaler.view.bookmarks.recyclerview.BookmarkClickListener
import com.bnvs.metaler.view.bookmarks.recyclerview.BookmarksAdapter
import kotlinx.android.synthetic.main.activity_bookmark.view.*

@BindingAdapter("selectedBookmarkCategoryType")
fun selectedBookmarkCategoryType(view: View, categoryType: String) {
    when (categoryType) {
        "materials" -> {
            materialsButtonEnable(view, true)
            manufactureButtonEnable(view, false)
        }
        "manufacture" -> {
            materialsButtonEnable(view, false)
            manufactureButtonEnable(view, true)
        }
    }
}

private fun materialsButtonEnable(v: View, b: Boolean) {
    if (b) {
        v.materialsCategoryBtn.setTextColor(
            ContextCompat.getColor(
                v.context,
                R.color.colorPurple
            )
        )
        v.materialsBar.visibility = View.VISIBLE
    } else {
        v.materialsCategoryBtn.setTextColor(
            ContextCompat.getColor(
                v.context,
                R.color.colorLightGrey
            )
        )
        v.materialsBar.visibility = View.INVISIBLE
    }
}

private fun manufactureButtonEnable(v: View, b: Boolean) {
    if (b) {
        v.manufactureCategoryBtn.setTextColor(
            ContextCompat.getColor(
                v.context,
                R.color.colorPurple
            )
        )
        v.manufactureBar.visibility = View.VISIBLE
    } else {
        v.manufactureCategoryBtn.setTextColor(
            ContextCompat.getColor(
                v.context,
                R.color.colorLightGrey
            )
        )
        v.manufactureBar.visibility = View.INVISIBLE
    }
}

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
