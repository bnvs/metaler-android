package com.bnvs.metaler.view.jobinput

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bnvs.metaler.R

@BindingAdapter("onJobChecked")
fun onJobChecked(view: TextView, onChecked: Boolean) {
    if (onChecked) {
        view.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
        view.setTextColor(ContextCompat.getColor(view.context, R.color.colorPurple))
    } else {
        view.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
        view.setTextColor(ContextCompat.getColor(view.context, R.color.colorLightGrey))
    }
}

@BindingAdapter("jobGroupVisibility")
fun jobGroupVisibility(view: Group, visibility: Boolean) {
    if (visibility) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}