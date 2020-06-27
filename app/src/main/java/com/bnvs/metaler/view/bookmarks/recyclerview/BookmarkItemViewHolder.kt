package com.bnvs.metaler.view.bookmarks.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.data.bookmarks.model.Bookmark
import com.bnvs.metaler.databinding.ItemBookmarkRvBinding

class BookmarkItemViewHolder(
    private val binding: ItemBookmarkRvBinding,
    listener: BookmarkClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = listener
    }

    fun bind(item: Bookmark) {
        with(binding) {
            position = adapterPosition
            postItem = item
            executePendingBindings()
        }
    }
}