package com.bnvs.metaler.util.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.posts.model.Post
import com.bnvs.metaler.databinding.ItemPostsRvBinding
import com.bnvs.metaler.util.posts.listener.PostClickListener
import com.bnvs.metaler.util.posts.viewholder.PostItemViewHolder
import com.bnvs.metaler.util.posts.viewholder.PostLoadingViewHolder

class PostsAdapter(private val listener: PostClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private val posts = mutableListOf<Post?>()

    fun replaceAll(list: List<Post>) {
        list.let {
            posts.clear()
            posts.addAll(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = DataBindingUtil.inflate<ItemPostsRvBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_posts_rv,
                    parent,
                    false
                )
                PostItemViewHolder(
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
        return posts.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (posts[position]) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_ITEM -> {
                val viewHolder = holder as PostItemViewHolder
                viewHolder.bind(posts[position]!!)
            }
        }
    }
}