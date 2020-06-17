package com.bnvs.metaler.util.posts.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.databinding.ItemPostsRvBinding
import com.bnvs.metaler.util.posts.listener.PostClickListener

class PostItemViewHolder(
    private val binding: ItemPostsRvBinding,
    listener: PostClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = listener
    }

    fun bind(item: Post) {
        with(binding) {
            position = adapterPosition
            postItem = item
            executePendingBindings()
        }
    }
}