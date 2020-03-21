package com.example.metaler_android.data.categories.source

import com.example.metaler_android.data.categories.Categories

interface CategoriesDataSource {

    interface LoadCategoriesCallback {
        fun onMaterialsLoaded(materials: Categories)
        fun onDataNotAvailable()
    }

    fun getCatagories(callback: LoadCategoriesCallback)

}