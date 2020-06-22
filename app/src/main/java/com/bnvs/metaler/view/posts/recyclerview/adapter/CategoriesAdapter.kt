package com.bnvs.metaler.view.posts.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bnvs.metaler.R
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.databinding.ItemMaterialsCategoryRvBinding
import com.bnvs.metaler.view.posts.recyclerview.listener.CategoryClickListener
import com.bnvs.metaler.view.posts.recyclerview.viewholder.CategoryItemViewHolder

class CategoriesAdapter(private val listener: CategoryClickListener) :
    RecyclerView.Adapter<CategoryItemViewHolder>() {

    private val categories = mutableListOf<Category>()
    private var selectedCategoryId = 0

    fun replaceAll(list: List<Category>) {
        list.let {
            categories.clear()
            categories.addAll(it)
        }
    }

    fun selectItem(categoryId: Int) {
        selectedCategoryId = categoryId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemMaterialsCategoryRvBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_materials_category_rv,
            parent,
            false
        )

        return CategoryItemViewHolder(
            binding,
            listener
        )
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val isSelected = selectedCategoryId == categories[position].id
        holder.bind(categories[position], isSelected)
    }
}