package com.bnvs.metaler.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import kotlinx.android.synthetic.main.item_tag_rv.view.*

/**
 * 재료, 가공 탭의 태그 검색 리사이클러뷰에 사용할 어댑터입니다.
 * */
class TagSearchAdapter(
    private val itemListener: TagSearchItemListener
) : RecyclerView.Adapter<TagSearchAdapter.ViewHolder>() {

    var tags = ArrayList<String>()

    var selectedPosition: Int = 0

    fun addTags(tagName: String) {
        tags.add(tagName)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag_rv, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tags[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var view: View = itemView

        fun bind(item: String, position: Int) {
            view.apply {
                tagName.apply {
                    text = item
                }
                tagDeleteBtn.setOnClickListener { itemListener.onTagDeleteBtnClick(position) }
            }

        }
    }
}

