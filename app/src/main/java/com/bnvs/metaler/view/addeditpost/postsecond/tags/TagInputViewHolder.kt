package com.bnvs.metaler.view.addeditpost.postsecond.tags

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.databinding.ItemTagInputBinding
import com.bnvs.metaler.util.setOnSingleClickListener

class TagInputViewHolder(
    private val binding: ItemTagInputBinding,
    tagClick: (adapterPosition: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnSingleClickListener(
            View.OnClickListener { tagClick(adapterPosition) }
        )
    }

    fun bind(tag: String) {
        with(binding) {
            tagName = tag
            executePendingBindings()
        }
    }


}