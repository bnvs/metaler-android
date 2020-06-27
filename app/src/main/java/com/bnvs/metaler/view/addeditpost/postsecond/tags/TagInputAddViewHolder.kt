package com.bnvs.metaler.view.addeditpost.postsecond.tags

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.util.setOnSingleClickListener

class TagInputAddViewHolder(
    itemView: View,
    addTagClick: () -> Unit
) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnSingleClickListener(
            View.OnClickListener { addTagClick() }
        )
    }

}