package com.bnvs.metaler.ui.mypage

import android.content.Context
import android.util.Log
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.data.token.source.repository.TokenRepository
import com.bnvs.metaler.data.user.deactivation.source.UserDeactivationRepository
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.source.UserModificationRepository
import com.bnvs.metaler.network.NetworkUtil
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.UnLinkResponseCallback

class PresenterMyPage(
    context: Context,
    private val view: ContractMyPage.View
) : ContractMyPage.Presenter {

    private val profileRepository = ProfileRepository(context)
    private val userModificationRepository = UserModificationRepository()
    private val userDeactivationRepository = UserDeactivationRepository()
    private val tokenRepository = TokenRepository(context)
    private lateinit var profile: Profile

    override fun start() {
        loadProfile()
    }

    override fun loadProfile() {
        profileRepository.getProfile(
            onProfileLoaded = { profile ->
                this.profile = profile
                view.showProfile(profile)
            },
            onProfileNotExist = {
                view.showProfileNotExistToast()
            }
        )
    }

    override fun modifyNickName(nickname: String) {
        userModificationRepository.modifyNickname(
            Nickname(nickname),
            onSuccess = {
                Log.d("닉네임 수정 성공", "닉네임 수정 성공")
                profileRepository.modifyNickname(
                    nickname,
                    onSuccess = {
                        loadProfile()
                    },
                    onFailure = {
                        view.showLocalNicknameChangeFailedToast()
                    }
                )
            },
            onFailure = {
                view.showLocalNicknameChangeFailedToast()
            }
        )
    }

    override fun logout() {
        userDeactivationRepository.logout(
            onSuccess = {
                deleteTokens()
                kakaoLogout()
            },
            onFailure = { e ->
                view.run {
                    showErrorToast("로그아웃 실패\n${NetworkUtil.getErrorMessage(e)}")
                    openLoginActivity()
                }
            }
        )
    }

    override fun withdrawal(confirmInput: String) {
        if (confirmInput == "동의합니다") {
            userDeactivationRepository.deleteUser(
                onSuccess = {
                    deleteTokens()
                    kakaoWithdrawal()
                },
                onFailure = { e ->
                    view.run {
                        showErrorToast("회원탈퇴 실패\n${NetworkUtil.getErrorMessage(e)}")
                        openLoginActivity()
                    }
                }
            )
        } else {
            view.showInvalidWithdrawalConfirmInputToast()
        }
    }

    override fun deleteTokens() {
        tokenRepository.deleteTokens()
    }

    private fun kakaoLogout() {
        UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                view.openLoginActivity()
            }
        })
    }

    private fun kakaoWithdrawal() {
        UserManagement.getInstance().requestUnlink(object : UnLinkResponseCallback() {
            override fun onSuccess(result: Long?) {
                view.openLoginActivity()
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                view.showErrorToast("회원탈퇴 실패\n$errorResult")
                view.openLoginActivity()
            }
        })
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
