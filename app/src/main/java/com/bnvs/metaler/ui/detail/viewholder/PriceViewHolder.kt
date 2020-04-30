package com.bnvs.metaler.ui.detail.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.ui.detail.PostRatingListener
import kotlinx.android.synthetic.main.item_detail_price.view.*

class PriceViewHolder(
    itemView: View,
    listener: PostRatingListener
) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.dislikeBtn.setOnClickListener { listener.onDislikeButtonClick() }
        itemView.likeBtn.setOnClickListener { listener.onLikeButtonClick() }
    }

    fun bind(postDetails: PostDetails) {
        itemView.apply {
            price.text = postDetails.price.toString()
            priceType.text = postDetails.price_type
            dislikeNum.text = postDetails.disliked.toString()
            likeNum.text = postDetails.liked.toString()
            when (postDetails.rating) {
                1 -> {
                    likeBtn.isChecked = true
                    dislikeBtn.isChecked = false
                }
                -1 -> {
                    likeBtn.isChecked = false
                    dislikeBtn.isChecked = true
                }
                else -> {
                    likeBtn.isChecked = false
                    dislikeBtn.isChecked = false
                }
            }
        }
    }
}