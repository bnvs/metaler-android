package com.bnvs.metaler.view.postfirst

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.postdetails.model.AttachImage
import com.bnvs.metaler.databinding.ItemThumbnailRvBinding
import kotlinx.android.synthetic.main.item_thumbnail_rv.view.*

class ThumbnailViewHolder(
    private val binding: ItemThumbnailRvBinding,
    itemClick: (adapterPosition: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.apply {
            setOnClickListener { itemClick(adapterPosition) }
            setOnLongClickListener {
                itemClick(adapterPosition)
                true
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            itemView.thumbnailLoading.indeterminateDrawable.colorFilter =
                BlendModeColorFilter(R.color.colorLightPurple, BlendMode.SRC_ATOP)
        } else {
            itemView.thumbnailLoading.indeterminateDrawable.setColorFilter(
                Color.GRAY,
                PorterDuff.Mode.MULTIPLY
            )
        }
    }

    fun bind(attachImage: AttachImage) {
        with(binding) {
            thumbnailItem = attachImage
            executePendingBindings()
        }
    }

}