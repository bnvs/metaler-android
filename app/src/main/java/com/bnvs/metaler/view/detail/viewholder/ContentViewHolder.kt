package com.bnvs.metaler.view.detail.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.addeditpost.model.PostTag
import com.bnvs.metaler.data.postdetails.model.PostDetails
import com.bnvs.metaler.view.detail.adapter.ContentImageAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.item_detail_content.view.*

class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageAdapter = ContentImageAdapter()

    fun bind(postDetails: PostDetails) {
        itemView.apply {
            title.text = postDetails.title
            userName.text = postDetails.nickname
            date.text = postDetails.date
            content.text = postDetails.content
            tags.text = getTagString(postDetails.tags)

            if (postDetails.attachs.isNotEmpty()) {
                contentImgRV.visibility = View.VISIBLE
                contentImgRV.adapter = imageAdapter
                imageAdapter.setImages(postDetails.attachs)
            }

            Glide.with(this)
                .load(postDetails.profile_url)
                .error(R.drawable.ic_profile_x3)
                .override(200, 200)
                .transform(CircleCrop())
                .into(userImg)
        }
    }

    private fun getTagString(tags: List<PostTag>): String {
        var tagString = ""
        for (tag in tags) {
            tagString += "#${tag.name} "
        }
        return tagString
    }

}