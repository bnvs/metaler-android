package com.bnvs.metaler_android.data.categories.source

import com.bnvs.metaler_android.data.categories.Categories

interface CategoriesDataSource {

    interface LoadCategoriesCallback {
        fun onMaterialsLoaded(materials: Categories)
        fun onDataNotAvailable()
    }

    fun getCatagories(callback: LoadCategoriesCallback)

}