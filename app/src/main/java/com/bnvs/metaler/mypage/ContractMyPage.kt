package com.bnvs.metaler.mypage

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.profile.Profile
import com.bnvs.metaler.util.TapBarContract

interface ContractMyPage {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showProfile(profile: Profile)

        fun showJobModifyUi()

        fun showMyPostsUi()

        fun showTermsCheckUi()

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