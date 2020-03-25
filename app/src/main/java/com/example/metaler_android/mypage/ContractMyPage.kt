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

        fun showTermsUi()

        fun showNicknameModifyDialog()

        fun showLogoutDialog()

        fun showWithdrawalDialog()

        fun showSuccessDialog()

    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadProfile()

        fun modifyJob()

        fun modifyNickName()

        fun logout()

        fun withdrawal()

        fun openMyPosts()

        fun openJobModify()

        fun openNicknameModify()

        fun openLogout()

        fun openTerms()

        fun openWithdrawal()


    }
}