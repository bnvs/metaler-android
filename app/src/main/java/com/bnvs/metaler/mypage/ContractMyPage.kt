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