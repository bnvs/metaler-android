package com.bnvs.metaler.view.posts.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.databinding.ItemMaterialsCategoryRvBinding
import com.bnvs.metaler.view.posts.recyclerview.listener.CategoryClickListener

class CategoryItemViewHolder(
    private val binding: ItemMaterialsCategoryRvBinding,
    listener: CategoryClickListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.listener = listener
    }

    fun bind(item: Category, isSelected: Boolean) {
        with(binding) {
            categoryItem = item
            isChecked = isSelected
            executePendingBindings()
        }
    }

}