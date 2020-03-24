package com.example.metaler_android.mypage

import com.example.metaler_android.BasePresenter
import com.example.metaler_android.BaseView
import com.example.metaler_android.data.profile.Profile
import com.example.metaler_android.util.TapBarContract

interface ContractMyPage {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showProfile(profile: Profile)

        fun showJobModifyUi()

        fun showMyPostsUi()

        fun showNicknameModifyDialog()

        fun showLogoutDialog()

        fun showWithdrawalDialog()

    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadProfile()

        fun openMyPosts()

        fun openJobModify()

        fun openNicknameModify()

        fun openLogout()

        fun openWithdrawal()

    }
}