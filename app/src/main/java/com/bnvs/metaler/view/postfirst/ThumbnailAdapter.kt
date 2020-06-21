package com.bnvs.metaler.view.postfirst

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.postdetails.model.AttachImage

class ThumbnailAdapter(private val itemClick: (adapterPosition: Int) -> Unit) :
    RecyclerView.Adapter<ThumbnailViewHolder>() {

    private val images = mutableListOf<AttachImage>()

    fun replaceAll(list: List<AttachImage>) {
        list.let {
            images.clear()
            images.addAll(it)
        }
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
        holder.bind(images[position].url)
    }
}