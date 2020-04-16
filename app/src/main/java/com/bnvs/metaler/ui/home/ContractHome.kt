package com.bnvs.metaler.ui.home

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.homeposts.model.HomePost
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.util.TapBarContract

/**
 * Contract interface 는
 * View 와 Presenter 에 사용될 함수를 정의하고 관리하는데 사용
 * */

interface ContractHome {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showProfile(profile: Profile)

        fun showProfileNotExistToast()

        fun showMaterialsList(materials: List<HomePost>)

        fun showManufacturesList(manufactures: List<HomePost>)

        fun showLoadHomePostFailedDialog(errorMessage: String)

        fun showPostDetailUi(postId: Int)

        fun setTransparentStatusBar()
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadProfile()

        fun loadHomePost()

        fun openPostDetail(clickedHomePostId: Int)

        fun setStatusBar()
    }
}