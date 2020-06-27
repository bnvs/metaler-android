package com.bnvs.metaler.view.addeditpost.postsecond.tags

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.ItemTagGuideBinding
import com.bnvs.metaler.databinding.ItemTagInputBinding

class TagInputAdapter(
    private val guideText: String,
    private val tagClick: (adapterPosition: Int) -> Unit,
    private val addTagClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_TAG_INPUT = 0
        private const val TYPE_TAG_GUIDE = 1
        private const val TYPE_ADD_TAG = 2
    }

    private val tags = mutableListOf<String>()

    fun replaceAll(list: List<String>) {
        list.let {
            tags.clear()
            tags.addAll(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_TAG_INPUT -> {
                val binding = DataBindingUtil.inflate<ItemTagInputBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_tag_input,
                    parent,
                    false
                )
                return TagInputViewHolder(binding, tagClick)
            }
            TYPE_TAG_GUIDE -> {
                val binding = DataBindingUtil.inflate<ItemTagGuideBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_tag_guide,
                    parent,
                    false
                )
                return TagInputGuideViewHolder(binding, addTagClick)
            }
            else -> {
                val inflatedView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_tag_add, parent, false)
                return TagInputAddViewHolder(inflatedView, addTagClick)
            }
        }
    }

    override fun getItemCount(): Int {
        return tags.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (tags.isEmpty()) {
            TYPE_TAG_GUIDE
        } else {
            when (position) {
                tags.size -> TYPE_ADD_TAG
                else -> TYPE_TAG_INPUT
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_TAG_INPUT -> {
                (holder as TagInputViewHolder).bind(tags[position])
            }
            TYPE_TAG_GUIDE -> {
                (holder as TagInputGuideViewHolder).bind(guideText)
            }
        }
    }

}