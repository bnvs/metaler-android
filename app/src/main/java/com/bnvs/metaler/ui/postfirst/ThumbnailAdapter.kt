package com.bnvs.metaler.ui.postfirst

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R

class ThumbnailAdapter(private val itemClick: (adapterPosition: Int) -> Unit) :
    RecyclerView.Adapter<ThumbnailViewHolder>() {

    private val images = mutableListOf<String>()

    fun setImages(list: List<String>) {
        this.images.addAll(list)
        notifyDataSetChanged()
    }

    fun addImage(imageUrl: String) {
        this.images.add(imageUrl)
        notifyDataSetChanged()
    }

    fun deleteImage(imageIndex: Int) {
        this.images.removeAt(imageIndex)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThumbnailViewHolder {
        val inflatedView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_thumbnail_rv, parent, false)
        return ThumbnailViewHolder(inflatedView, itemClick)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ThumbnailViewHolder, position: Int) {
        holder.bind(images[position])
    }
}