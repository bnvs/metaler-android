package com.bnvs.metaler.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import com.bnvs.metaler.data.profile.Profile
import com.bnvs.metaler.data.profile.source.ProfileRepository
import com.bnvs.metaler.data.token.AccessToken
import com.bnvs.metaler.data.token.SigninToken
import com.bnvs.metaler.data.token.source.TokenRepository
import com.bnvs.metaler.data.user.certification.model.AddUserRequest
import com.bnvs.metaler.data.user.certification.model.CheckMembershipRequest
import com.bnvs.metaler.data.user.certification.model.LoginRequest
import com.bnvs.metaler.data.user.certification.model.User
import com.bnvs.metaler.data.user.certification.source.UserCertificationRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.ui.home.ActivityHome
import com.bnvs.metaler.ui.termsagree.ActivityTermsAgree
import com.bnvs.metaler.util.DeviceInfo
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException

class ActivityLogin : AppCompatActivity() {

    private val TAG = "ActivityLogin"

    private lateinit var callback: SessionCallback
    private lateinit var tokenRepository: TokenRepository
    private lateinit var userRepository: UserCertificationRepository
    private lateinit var profileRepository: ProfileRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tokenRepository = TokenRepository(this@ActivityLogin)
        userRepository = UserCertificationRepository()
        profileRepository = ProfileRepository(this@ActivityLogin)

        // SessionCallback 초기화
        callback = SessionCallback()
        // 현재 세션에 callback 붙이기
        Session.getCurrentSession().addCallback(callback)
        // 현재 앱에 유효한 카카오 로그인 토큰이 있다면 바로 로그인(자동 로그인과 유사)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    private inner class SessionCallback : ISessionCallback {
        override fun onSessionOpened() {
            // 로그인 세션이 열렸을 때
            UserManagement.getInstance().me(object : MeV2ResponseCallback() {
                override fun onSuccess(result: MeV2Response?) =
                    // 카카오 로그인 성공시 local 에서 signin_token 불러오기
                    if (result != null) {
                        getSigninToken(result)
                    } else {
                        makeToast("카카오 로그인 응답값이 없습니다")
                    }

                override fun onSessionClosed(errorResult: ErrorResult?) =
                    // 로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                    makeToast("세션이 닫혔습니다. 다시 시도해주세요 : ${errorResult.toString()}")
            })
        }

        override fun onSessionOpenFailed(exception: KakaoException?) =
            // 로그인 세션이 정상적으로 열리지 않았을 때
            if (exception != null) {
                com.kakao.util.helper.log.Logger.e(exception)
                makeToast("로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요 : $exception")
            } else {
                makeToast("로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요")
            }
    }

    private fun getSigninToken(result: MeV2Response) {
        val kakaoId = result.id.toString()
        tokenRepository.getSigninToken(
            onTokenLoaded = { token ->
                login(kakaoId, token.signin_token)
            },
            onTokenNotExist = {
                checkMembership(result)
            }
        )
    }

    private fun saveSigninToken(token: String) {
        tokenRepository.saveSigninToken(SigninToken(token))
    }

    private fun saveAccessToken(token: String) {
        tokenRepository.saveAccessToken(AccessToken(token))
    }

    private fun saveProfileData(user: User) {
        profileRepository.saveProfile(
            Profile(
                user.profile_nickname,
                user.profile_image_url,
                user.profile_email
            )
        )
    }

    private fun checkMembership(result: MeV2Response) {
        val kakaoId = result.id.toString()
        userRepository.checkMembership(
            CheckMembershipRequest(kakaoId),
            onSuccess = { response ->
                when (response.message) {
                    "you_can_join" -> {
                        openTermsAgree(makeAddUserRequest(result))
                    }
                    "you_can_login" -> {
                        saveSigninToken(response.signin_token)
                        login(kakaoId, response.signin_token)
                    }
                    else -> {
                        makeToast("회원가입 여부 확인 도중, 알 수 없는 에러가 발생했습니다")
                    }
                }
            },
            onFailure = { e ->
                makeToast("회원가입 여부 확인 실패 : ${NetworkUtil.getErrorMessage(e)}")
            }
        )
    }

    // login api 요청 request body 반환하는 함수
    private fun loginRequest(kakao_id: String, signin_token: String): LoginRequest {
        val deviceInfo = DeviceInfo(this)
        val loginRequest = LoginRequest(
            kakao_id,
            signin_token,
            "push_token",
            deviceInfo.getDeviceId(),
            deviceInfo.getDeviceModel(),
            deviceInfo.getDeviceOs(),
            deviceInfo.getAppVersion()
        )
        Log.d(TAG, "로그인 request : $loginRequest")
        return loginRequest
    }

    // login api 호출
    private fun login(kakao_id: String, signin_token: String) {
        userRepository.login(
            loginRequest(kakao_id, signin_token),
            onSuccess = { response ->
                saveAccessToken(response.access_token)
                saveProfileData(response.user)
                openHome()
            },
            onFailure = { e ->
                makeToast("로그인 실패 : ${NetworkUtil.getErrorMessage(e)}")
            }
        )
    }

    private fun openTermsAgree(addUserRequest: AddUserRequest) {
        val intent = Intent(this, ActivityTermsAgree::class.java)
        intent.putExtra("addUserRequest", addUserRequest)
        startActivity(intent)
        finish()
    }

    private fun openHome() {
        val intent = Intent(this, ActivityHome::class.java)
        startActivity(intent)
        finish()
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityLogin, message, Toast.LENGTH_SHORT).show()
    }

    private fun makeAddUserRequest(result: MeV2Response): AddUserRequest {
        return AddUserRequest(
            result.id.toString(),
            result.properties["nickname"].toString(),
            result.properties["profile_image"].toString(),
            result.kakaoAccount.email,
            makeGenderText(result.kakaoAccount.gender.toString()),
            null,
            null,
            null,
            0
        )
    }

    private fun makeGenderText(kakaoGender: String): String? {
        return when (kakaoGender) {
            "MALE" -> "M"
            "FEMALE" -> "W"
            else -> null
        }
    }

}