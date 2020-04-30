package com.bnvs.metaler.ui.detail.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.comments.model.Comment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.item_detail_comment_bottom.view.*

class CommentBottomViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(comment: Comment) {
        itemView.apply {
            userName.text = comment.nickname
            date.text = comment.date
            commentContent.text = comment.content

            Glide.with(this)
                .load(comment.profile_url)
                .error(R.drawable.ic_profile_x3)
                .transform(CircleCrop())
                .into(userImg)
        }
    }

}