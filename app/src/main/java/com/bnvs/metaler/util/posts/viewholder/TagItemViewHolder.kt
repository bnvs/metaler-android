package com.bnvs.metaler.util.posts.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.databinding.ItemTagRvBinding
import com.bnvs.metaler.util.posts.listener.TagClickListener
import com.bnvs.metaler.util.setOnSingleClickListener

class TagItemViewHolder(
    private val binding: ItemTagRvBinding,
    listener: TagClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnSingleClickListener(
            View.OnClickListener { listener.onTagClick(adapterPosition) }
        )
    }

    fun bind(item: String) {
        with(binding) {
            tagName = item
            executePendingBindings()
        }
    }

}