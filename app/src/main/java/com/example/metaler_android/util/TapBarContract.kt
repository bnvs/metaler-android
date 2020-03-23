package com.example.metaler_android.util

import android.content.Context

interface TapBarContract {

    interface View {
        fun showHomeUi()

        fun showMaterialsUi()

        fun showManufacturesUi()

        fun showBookmarksUi()

        fun showMyPageUi()
    }

    interface Presenter {
        fun openHome()

        fun openMaterials()

        fun openManufactures()

        fun openBookmarks()

        fun openMyPage()
    }

}