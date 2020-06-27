package com.bnvs.metaler.view.addeditpost.postsecond

import android.view.View
import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.view.addeditpost.postsecond.tags.TagInputAdapter
import com.bnvs.metaler.view.addeditpost.postsecond.tagsuggest.HashTagSuggestAdapter

@BindingAdapter("workTagInputVisibility")
fun workTagInputVisibility(view: View, categoryType: String) {
    when (categoryType) {
        "materials" -> view.visibility = View.GONE
        "manufacture" -> view.visibility = View.VISIBLE
    }
}

@BindingAdapter("setTagInputItems")
fun setTagInputItems(view: RecyclerView, items: List<String>?) {
    (view.adapter as? TagInputAdapter)?.run {
        items?.let { replaceAll(it) }
        notifyDataSetChanged()
    }
}

@BindingAdapter("setTagSuggestionItems")
fun setTagSuggestionItems(view: AutoCompleteTextView, items: List<String>?) {
    (view.adapter as? HashTagSuggestAdapter)?.run {
        items?.let { setSuggests(it) }
        notifyDataSetChanged()
    }
}