package com.bnvs.metaler.view.addeditpost.postsecond.tags

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.databinding.ItemTagGuideBinding
import com.bnvs.metaler.util.setOnSingleClickListener

class TagInputGuideViewHolder(
    private val binding: ItemTagGuideBinding,
    addTagClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnSingleClickListener(
            View.OnClickListener { addTagClick() }
        )
    }

    fun bind(guideText: String) {
        with(binding) {
            hint = guideText
            executePendingBindings()
        }
    }

}