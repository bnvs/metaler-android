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
import com.bnvs.metaler.data.token.source.TokenDataSource
import com.bnvs.metaler.data.token.source.TokenRepository
import com.bnvs.metaler.data.user.certification.model.*
import com.bnvs.metaler.data.user.certification.source.UserCertificationDataSource
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
import retrofit2.HttpException

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
                override fun onSuccess(result: MeV2Response?) {
                    // 카카오 로그인이 성공했을 때
                    Log.d(TAG, "카카오 아이디 : ${result!!.id}")
                    val kakaoId = result.id.toString()

                    // local 에 signin_token 존재하는지 확인
                    tokenRepository.getSigninToken(object :
                        TokenDataSource.LoadSigninTokenCallback {
                        override fun onTokenloaded(token: SigninToken) {
                            val signinToken = token.signin_token
                            login(kakaoId, signinToken)
                        }

                        override fun onTokenNotExist() {
                            // signin_token 존재하지 않음, 회원가입 여부확인 api 호출
                            userRepository.checkMembership(
                                CheckMembershipRequest(
                                    kakaoId
                                ),
                                object : UserCertificationDataSource.CheckMembershipCallback {
                                    override fun onMembershipChecked(response: CheckMembershipResponse) {
                                        when (response.message) {
                                            "you_can_join" -> {
                                                openTermsAgree(makeAddUserRequest(result))
                                            }
                                            else -> {
                                                tokenRepository.saveSigninToken(
                                                    SigninToken(response.signin_token)
                                                )
                                                login(kakaoId, response.signin_token)
                                            }
                                        }
                                    }

                                    override fun onResponseError(exception: HttpException) {
                                        val error =
                                            NetworkUtil.getErrorResponse(exception.response().errorBody()!!)
                                        Log.d(TAG, "회원가입 여부 확인 실패 : $error")
                                    }

                                    override fun onFailure(t: Throwable) {
                                        Log.d(TAG, "회원가입 여부 확인 실패 : $t")
                                    }
                                })

                        }
                    })
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    // 로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                    makeToast("세션이 닫혔습니다. 다시 시도해주세요 : ${errorResult.toString()}")
                }
            })
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            // 로그인 세션이 정상적으로 열리지 않았을 때
            if (exception != null) {
                com.kakao.util.helper.log.Logger.e(exception)
                makeToast("로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요 : $exception")
            }
        }

    }

    // login api 요청 request body 반환하는 함수
    private fun loginRequest(kakao_id: String, signin_token: String): LoginRequest {
        val deviceInfo = DeviceInfo(this)
        val loginRequest = LoginRequest(
            kakao_id,
            "에러바디테스트중",
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
            object : UserCertificationDataSource.LoginCallback {
                override fun onLoginSuccess(response: LoginResponse) {
                    // 발급받은 access_token local 에 저장
                    tokenRepository.saveAccessToken(
                        AccessToken(response.access_token)
                    )
                    // response 의 User 에서 profile 정보 추출하여 로컬에 저장
                    profileRepository.saveProfile(
                        Profile(
                            response.user.profile_nickname,
                            response.user.profile_image_url,
                            response.user.profile_email
                        )
                    )
                    // 홈탭 실행
                    openHome()
                }

                override fun onResponseError(exception: HttpException) {
                    val error =
                        NetworkUtil.getErrorResponse(exception.response().errorBody()!!)
                    Log.d("로그인 실패", "$error")
                }

                override fun onFailure(t: Throwable) {
                    Log.d(TAG, "로그인 실패 : $t")
                }
            })
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