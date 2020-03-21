package com.example.metaler_android.data.category.source

import com.example.metaler_android.data.category.Categories

interface CategoryDataSource {

    interface LoadMaterialsCallback {
        fun onMaterialsLoaded(materials: Categories)
        fun onDataNotAvailable()
    }

    fun getMaterials(callback: LoadMaterialsCallback)

}