package com.example.metaler_android.home

import android.content.Context
import com.example.metaler_android.BasePresenter
import com.example.metaler_android.BaseView
import com.example.metaler_android.data.homepost.HomePost
import com.example.metaler_android.data.profile.Profile

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractHome {
    interface View : BaseView<Presenter> {
        fun showProfile(profile: Profile)

        fun showMaterialsList(materials: List<HomePost>)

        fun showManufacturesList(manufactures: List<HomePost>)

        fun showMaterialsUi()

        fun showManufacturesUi()

        fun showPostDetailUi(postId: Int)

        fun setTapBarListener(context: Context)

        fun setTransparentStatusBar()
    }

    interface Presenter : BasePresenter {
        fun loadProfile()

        fun loadHomePost()

        fun openMaterials()

        fun openManufactures()

        fun openPostDetail(postId: Int)

        fun setTapBar(context: Context)

        fun setStatusBar()
    }
}