package com.bnvs.metaler.util.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.ItemTagRvBinding
import com.bnvs.metaler.util.posts.listener.TagClickListener
import com.bnvs.metaler.util.posts.viewholder.TagItemViewHolder

class TagsAdapter(private val listener: TagClickListener) :
    RecyclerView.Adapter<TagItemViewHolder>() {

    private val tags = mutableListOf<String>()

    fun replaceAll(list: List<String>) {
        list.let {
            tags.clear()
            tags.addAll(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemTagRvBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_tag_rv,
            parent,
            false
        )
        return TagItemViewHolder(
            binding,
            listener
        )
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: TagItemViewHolder, position: Int) {
        holder.bind(tags[position])
    }
}