package com.bnvs.metaler.ui.mypage

import android.content.Context
import android.util.Log
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.source.UserModificationRepository

class PresenterMyPage(
    context: Context,
    private val view: ContractMyPage.View
) : ContractMyPage.Presenter {

    private val profileRepository = ProfileRepository(context)
    private val userModificationRepository = UserModificationRepository()

    override fun start() {
        loadProfile()
    }

    override fun loadProfile() {
        profileRepository.getProfile(
            onProfileLoaded = { profile ->
                view.showProfile(profile)
            },
            onProfileNotExist = {
                view.showProfileNotExistToast()
            }
        )
    }

    override fun logout() {

    }

    override fun modifyJob() {

    }

    override fun modifyNickName(nickname: String) {
        userModificationRepository.modifyNickname(
            Nickname(nickname),
            onSuccess = {
                Log.d("닉네임 수정 성공", "닉네임 수정 성공")
            },
            onFailure = {

            }
        )
    }

    override fun withdrawal() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openTerms() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openJobModify() {
        view.showJobModifyUi()
    }

    override fun openMyPosts() {
        view.showMyPostsUi()
    }

    override fun openNicknameModify() {
        view.showNicknameModifyDialog()
    }

    override fun openLogout() {
        view.showLogoutDialog()
    }

    override fun openWithdrawal() {
        view.showWithdrawalDialog()
    }

}
