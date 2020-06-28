package com.bnvs.metaler.view.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.data.token.source.repository.TokenRepository
import com.bnvs.metaler.data.user.certification.source.repository.UserCertificationRepository
import com.bnvs.metaler.data.user.deactivation.source.repository.UserDeactivationRepository
import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.tap.BaseTapViewModel
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.callback.UnLinkResponseCallback

class ViewModelMyPage(
    private val profileRepository: ProfileRepository,
    private val userCertificationRepository: UserCertificationRepository,
    private val userModificationRepository: UserModificationRepository,
    private val userDeactivationRepository: UserDeactivationRepository,
    private val tokenRepository: TokenRepository
) : BaseTapViewModel() {

    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profile

    private val _appVersion = MutableLiveData<String>()
    val appVersion: LiveData<String> = _appVersion

    private val _openNicknameModifyDialog = MutableLiveData<Boolean>().apply { value = false }
    val openNicknameModifyDialog: LiveData<Boolean> = _openNicknameModifyDialog
    private val _openJobModifyActivity = MutableLiveData<Boolean>().apply { value = false }
    val openJobModifyActivity: LiveData<Boolean> = _openJobModifyActivity
    private val _openMyPostsActivity = MutableLiveData<Boolean>().apply { value = false }
    val openMyPostsActivity: LiveData<Boolean> = _openMyPostsActivity
    private val _openTermsCheckActivity = MutableLiveData<Boolean>().apply { value = false }
    val openTermsCheckActivity: LiveData<Boolean> = _openTermsCheckActivity
    private val _openLogoutDialog = MutableLiveData<Boolean>().apply { value = false }
    val openLogoutDialog: LiveData<Boolean> = _openLogoutDialog
    private val _openWithdrawalDialog = MutableLiveData<Boolean>().apply { value = false }
    val openWithdrawalDialog: LiveData<Boolean> = _openWithdrawalDialog
    private val _openLoginActivity = MutableLiveData<Boolean>().apply { value = false }
    val openLoginActivity: LiveData<Boolean> = _openLoginActivity


    init {
        loadProfile()
        getAppVersion()
    }

    private fun loadProfile() {
        profileRepository.getProfile(
            onProfileLoaded = { profile ->
                _profile.value = profile
            },
            onProfileNotExist = {
                _errorToastMessage.setMessage("프로필 데이터를 불러오는데 실패했습니다")
            }
        )
    }

    private fun getAppVersion() {
        userCertificationRepository.getDeviceInfo().getAppVersion().let {
            _appVersion.value = it
        }
    }

    fun openModifyNicknameDialog() {
        _openNicknameModifyDialog.enable()
    }

    fun modifyNickname(nickname: String) {
        if (profile.value?.profile_nickname == nickname.trim()) {
            return
        }

        userModificationRepository.let {
            it.modifyNickname(
                Nickname(nickname.trim()),
                onSuccess = {
                    it.modifyLocalNickname(
                        Nickname(nickname.trim()),
                        onSuccess = { loadProfile() },
                        onFailure = { _errorToastMessage.setMessage("로컬 닉네임 수정 실패") }
                    )
                },
                onFailure = { e ->
                    _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
                },
                handleError = { e -> _errorCode.setErrorCode(e) }
            )
        }
    }

    fun logout() {
        userDeactivationRepository.logout(
            onSuccess = {
                deleteTokens()
                kakaoLogout()
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
                openLoginActivity()
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    fun withdrawal(confirmInput: String) {
        if (confirmInput == "동의합니다") {
            userDeactivationRepository.deleteUser(
                onSuccess = {
                    deleteTokens()
                    kakaoWithdrawal()
                },
                onFailure = { e ->
                    _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
                    openLoginActivity()
                },
                handleError = { e -> _errorCode.setErrorCode(e) }
            )
        } else {
            _errorDialogMessage.setMessage("\"동의합니다\"를 올바르게 입력해주세요")
        }
    }

    private fun deleteTokens() {
        tokenRepository.deleteTokens()
    }

    private fun kakaoLogout() {
        UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                _errorToastMessage.setMessage("로그아웃 되었습니다")
                openLoginActivity()
            }
        })
    }

    private fun kakaoWithdrawal() {
        UserManagement.getInstance().requestUnlink(object : UnLinkResponseCallback() {
            override fun onSuccess(result: Long?) {
                _errorToastMessage.setMessage(
                    "회원탈퇴가 완료되었습니다.\n" +
                            "그동안 메탈러 서비스를 이용해 주셔서 감사합니다."
                )
                openLoginActivity()
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                _errorToastMessage.setMessage("회원탈퇴 실패\n$errorResult")
                openLoginActivity()
            }
        })
    }

    fun openJobModifyActivity() {
        _openJobModifyActivity.enable()
    }

    fun openMyPostsActivity() {
        _openMyPostsActivity.enable()
    }

    fun openTermsCheckActivity() {
        _openTermsCheckActivity.enable()
    }

    fun openLogoutDialog() {
        _openLogoutDialog.enable()
    }

    fun openWithdrawalDialog() {
        _openWithdrawalDialog.enable()
    }

    private fun openLoginActivity() {
        _openLoginActivity.enable()
    }


}