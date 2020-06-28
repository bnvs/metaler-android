package com.bnvs.metaler.view.myposts.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.myposts.model.MyPost
import com.bnvs.metaler.databinding.ItemMyPostsRvBinding
import com.bnvs.metaler.view.posts.recyclerview.viewholder.PostLoadingViewHolder

class MyPostsAdapter(
    private var listener: MyPostClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    private val myPosts = mutableListOf<MyPost?>()

    fun replaceAll(list: List<MyPost>) {
        list.let {
            myPosts.clear()
            myPosts.addAll(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = DataBindingUtil.inflate<ItemMyPostsRvBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_my_posts_rv,
                    parent,
                    false
                )
                MyPostItemViewHolder(
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
        return myPosts.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (myPosts[position]) {
            null -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_ITEM -> {
                val viewHolder = holder as MyPostItemViewHolder
                viewHolder.bind(myPosts[position]!!)
            }
        }
    }
}