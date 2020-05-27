package com.bnvs.metaler.view.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.postdetails.model.AttachImage
import com.bnvs.metaler.view.detail.viewholder.ContentImageViewHolder

class ContentImageAdapter : RecyclerView.Adapter<ContentImageViewHolder>() {

    private val images = mutableListOf<AttachImage>()

    fun setImages(images: List<AttachImage>) {
        this.images.clear()
        this.images.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentImageViewHolder {
        val inflatedView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_content_img_rv, parent, false)
        return ContentImageViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ContentImageViewHolder, position: Int) {
        holder.bind(images[position].url)
    }
}