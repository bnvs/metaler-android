package com.bnvs.metaler.ui.mypage

import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.util.TapBarContract

interface ContractMyPage {
    interface View : BaseView<Presenter>, TapBarContract.View {
        fun showProfile(profile: Profile)

        fun showProfileNotExistToast()

        fun showLocalNicknameChangeFailedToast()

        fun showJobModifyUi()

        fun showMyPostsUi()

        fun showTermsCheckUi()

        fun showNicknameModifyDialog()

        fun showLogoutDialog()

        fun showWithdrawalDialog()

        fun openLoginActivity()

        fun showLogoutSuccessToast()

        fun showWithdrawalSuccessToast()

        fun showInvalidWithdrawalConfirmInputToast()

        fun showErrorToast(errorMessage: String)
    }

    interface Presenter : BasePresenter, TapBarContract.Presenter {
        fun loadProfile()

        fun modifyNickName(nickname: String)

        fun logout()

        fun withdrawal(confirmInput: String)

        fun openMyPosts()

        fun openJobModify()

        fun openNicknameModify()

        fun openLogout()

        fun openWithdrawal()

        fun deleteTokens()

        fun openTerms()
    }
}