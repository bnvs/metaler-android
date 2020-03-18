package com.example.metaler_android.data.material.source

import com.example.metaler_android.data.material.Materials

interface MaterialDataSource {

    interface LoadMaterialsCallback {
        fun onMaterialsLoaded(materials: Materials)
        fun onDataNotAvailable()
    }

    fun getMaterials(callback: LoadMaterialsCallback)

}