package com.bnvs.metaler.view.materials.category

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.categories.model.Category

@BindingAdapter("categoryTextColorChecked")
fun categoryTextColorChecked(view: TextView, isChecked: Boolean) {
    if (isChecked) {
        view.setTextColor(ContextCompat.getColor(view.context, R.color.colorPurple))
    } else {
        view.setTextColor(ContextCompat.getColor(view.context, R.color.colorLightGrey))
    }
}

@BindingAdapter("categoryViewChecked")
fun categoryViewChecked(view: View, isChecked: Boolean) {
    if (isChecked) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}


@BindingAdapter("setCategoryItems")
fun setCategoryItems(view: RecyclerView, items: List<Category>?) {
    (view.adapter as? CategoriesAdapter)?.run {
        items?.let { replaceAll(it) }
        notifyDataSetChanged()
    }
}

@BindingAdapter("selectedCategoryItem")
fun selectedCategoryItem(view: RecyclerView, selectedCategoryId: Int) {
    (view.adapter as? CategoriesAdapter)?.run {
        selectItem(selectedCategoryId)
        notifyDataSetChanged()
    }
}