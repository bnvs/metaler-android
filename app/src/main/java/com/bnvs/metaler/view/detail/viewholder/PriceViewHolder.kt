package com.bnvs.metaler.view.detail.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.view.detail.listener.PostRatingListener
import kotlinx.android.synthetic.main.item_detail_price.view.*
import java.text.NumberFormat

class PriceViewHolder(
    itemView: View,
    listener: PostRatingListener
) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.dislikeBtn.setOnClickListener {
            listener.onDislikeButtonClick()
            if (itemView.likeBtn.isChecked) {
                itemView.dislikeBtn.isChecked = false
            }
        }
        itemView.likeBtn.setOnClickListener {
            listener.onLikeButtonClick()
            if (itemView.dislikeBtn.isChecked) {
                itemView.likeBtn.isChecked = false
            }
        }
    }

    fun bind(postDetails: PostDetails) {
        itemView.apply {
            price.text = NumberFormat.getInstance().format(postDetails.price)
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