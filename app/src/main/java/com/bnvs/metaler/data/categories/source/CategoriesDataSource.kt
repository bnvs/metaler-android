package com.bnvs.metaler.data.categories.source

import com.bnvs.metaler.data.categories.Categories

interface CategoriesDataSource {

    interface LoadCategoriesCallback {
        fun onMaterialsLoaded(materials: Categories)
        fun onDataNotAvailable()
    }

    fun getCatagories(callback: LoadCategoriesCallback)

}