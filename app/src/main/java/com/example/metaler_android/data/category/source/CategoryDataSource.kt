package com.example.metaler_android.data.category.source

import com.example.metaler_android.data.category.Categories

interface CategoryDataSource {

    interface LoadCategoriesCallback {
        fun onMaterialsLoaded(materials: Categories)
        fun onDataNotAvailable()
    }

    fun getCatagories(callback: LoadCategoriesCallback)

}