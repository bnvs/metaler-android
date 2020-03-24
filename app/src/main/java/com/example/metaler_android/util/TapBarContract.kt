package com.example.metaler_android.util

import android.content.Context
import android.content.Intent
import com.example.metaler_android.bookmark.ActivityBookmark
import com.example.metaler_android.home.ActivityHome
import com.example.metaler_android.manufactures.ActivityManufactures
import com.example.metaler_android.materials.ActivityMaterials
import com.example.metaler_android.mypage.ActivityMyPage

interface TapBarContract {

    interface View {
        fun showHomeUi(context: Context) {
            Intent(context, ActivityHome::class.java).also {
                context.startActivity(it)
            }
        }

        fun showMaterialsUi(context: Context) {
            Intent(context, ActivityMaterials::class.java).also {
                context.startActivity(it)
            }
        }

        fun showManufacturesUi(context: Context) {
            Intent(context, ActivityManufactures::class.java).also {
                context.startActivity(it)
            }
        }

        fun showBookmarksUi(context: Context) {
            Intent(context, ActivityBookmark::class.java).also {
                context.startActivity(it)
            }
        }

        fun showMyPageUi(context: Context) {
            Intent(context, ActivityMyPage::class.java).also {
                context.startActivity(it)
            }
        }
    }

    interface Presenter {
        fun openHome()

        fun openMaterials()

        fun openManufactures()

        fun openBookmarks()

        fun openMyPage()
    }

}