package com.bnvs.metaler_android.util

import android.content.Context
import android.content.Intent
import com.bnvs.metaler_android.bookmark.ActivityBookmark
import com.bnvs.metaler_android.home.ActivityHome
import com.bnvs.metaler_android.manufactures.ActivityManufactures
import com.bnvs.metaler_android.materials.ActivityMaterials
import com.bnvs.metaler_android.mypage.ActivityMyPage

/**
 * 탭바가 있는 액티비티에서 사용하는 TapBarContract 입니다.
 * intent 로 화면을 넘기는 코드는 액티비티마다 같은 내용이 중복되어 작성되므로,
 * 코드의 간결화를 위해 interface default method 로 작성해두었습니다.
 * */

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
        fun openHome(view: View, context: Context) { view.showHomeUi(context) }

        fun openMaterials(view: View, context: Context) { view.showMaterialsUi(context) }

        fun openManufactures(view: View, context: Context) { view.showManufacturesUi(context) }

        fun openBookmarks(view: View, context: Context) { view.showBookmarksUi(context) }

        fun openMyPage(view: View, context: Context) { view.showMyPageUi(context) }
    }

}