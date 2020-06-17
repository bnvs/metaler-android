package com.bnvs.metaler.util.newadpaters

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import kotlinx.android.synthetic.main.item_loading.view.*

class PostLoadingViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            itemView.progressBar.indeterminateDrawable.colorFilter =
                BlendModeColorFilter(R.color.colorLightPurple, BlendMode.SRC_ATOP)
        } else {
            itemView.progressBar.indeterminateDrawable.setColorFilter(
                Color.GRAY,
                PorterDuff.Mode.MULTIPLY
            )
        }
    }

}