package com.bnvs.metaler.home

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.homeposts.HomePost
import com.bnvs.metaler.data.profile.Profile
import com.bnvs.metaler.util.TapBarContract

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractHome {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showProfile(profile: Profile)

        fun showMaterialsList(materials: List<HomePost>)

        fun showManufacturesList(manufactures: List<HomePost>)

        fun showPostDetailUi(postId: Int)

        fun setTransparentStatusBar()
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadProfile()

        fun loadHomePost()

        fun openPostDetail(postId: Int)

        fun setStatusBar()
    }
}