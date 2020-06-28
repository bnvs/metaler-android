package com.bnvs.metaler.view.myposts.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.databinding.ItemMyPostsRvBinding

class MyPostItemViewHolder(
    val binding: ItemMyPostsRvBinding,
    listener: MyPostClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = listener
    }

    fun bind(item: MyPost) {
        with(binding) {
            position = adapterPosition
            postItem = item
            executePendingBindings()
        }
    }
}