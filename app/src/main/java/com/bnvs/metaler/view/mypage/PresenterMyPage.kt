package com.bnvs.metaler.view.mypage

import android.content.Context
import android.util.Log
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.profile.source.local.ProfileLocalDataSourceImpl
import com.bnvs.metaler.data.profile.source.repository.ProfileRepositoryImpl
import com.bnvs.metaler.data.token.source.local.TokenLocalDataSourceImpl
import com.bnvs.metaler.data.token.source.repository.TokenRepositoryImpl
import com.bnvs.metaler.data.user.deactivation.source.remote.UserDeactivationRemoteDataSourceImpl
import com.bnvs.metaler.data.user.deactivation.source.repository.UserDeactivationRepositoryImpl
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.source.local.UserModificationLocalDataSourceImpl
import com.bnvs.metaler.data.user.modification.source.remote.UserModificationRemoteDataSourceImpl
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepositoryImpl
import com.bnvs.metaler.network.NetworkUtil
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.UnLinkResponseCallback

class PresenterMyPage(
    context: Context,
    private val view: ContractMyPage.View
) : ContractMyPage.Presenter {

    private val profileRepository = ProfileRepositoryImpl(
        ProfileLocalDataSourceImpl(context)
    )
    private val userModificationRepository = UserModificationRepositoryImpl(
        UserModificationLocalDataSourceImpl(context),
        UserModificationRemoteDataSourceImpl()
    )

    private val userDeactivationRepository =
        UserDeactivationRepositoryImpl(
            UserDeactivationRemoteDataSourceImpl()
        )
    private val tokenRepository = TokenRepositoryImpl(TokenLocalDataSourceImpl(context))
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
                userModificationRepository.modifyLocalNickname(
                    Nickname(nickname),
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
            },
            handleError = {

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
            },
            handleError = {

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
                },
                handleError = {

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
        view.showTermsCheckUi()
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
