package com.bnvs.metaler.view.bookmarks.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bnvs.metaler.databinding.ItemBookmarkRvBinding
import com.bnvs.metaler.view.posts.recyclerview.viewholder.PostLoadingViewHolder

class BookmarksAdapter(private val listener: BookmarkClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private val bookmarks = mutableListOf<Bookmark?>()

    fun replaceAll(list: List<Bookmark>) {
        list.let {
            bookmarks.clear()
            bookmarks.addAll(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = DataBindingUtil.inflate<ItemBookmarkRvBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_bookmark_rv,
                    parent,
                    false
                )
                BookmarkItemViewHolder(
                    binding,
                    listener
                )
            }
            else -> {
                val inflatedView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_loading, parent, false)
                PostLoadingViewHolder(
                    inflatedView
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return bookmarks.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (bookmarks[position]) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_ITEM -> {
                val viewHolder = holder as BookmarkItemViewHolder
                viewHolder.bind(bookmarks[position]!!)
            }
        }
    }
}