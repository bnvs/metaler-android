package com.bnvs.metaler.view.detail.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_detail_comment_count.view.*

class CommentCountViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(commentCount: Int) {
        itemView.commentNum.text = commentCount.toString()
    }
}