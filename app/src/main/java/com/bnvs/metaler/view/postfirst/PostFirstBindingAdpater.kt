package com.bnvs.metaler.view.postfirst

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("postLoadingViewVisibility")
fun postLoadingViewVisibility(view: View, visibility: Boolean) {
    if (visibility) {
        view.bringToFront()
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}