package com.bnvs.metaler.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bnvs.metaler.R
import com.bnvs.metaler.data.profile.Profile
import com.bnvs.metaler.data.profile.source.ProfileRepository
import com.bnvs.metaler.data.token.AccessToken
import com.bnvs.metaler.data.token.SigninToken
import com.bnvs.metaler.data.token.source.TokenDataSource
import com.bnvs.metaler.data.token.source.TokenRepository
import com.bnvs.metaler.data.user.*
import com.bnvs.metaler.data.user.source.UserDataSource
import com.bnvs.metaler.data.user.source.UserRepository
import com.bnvs.metaler.home.ActivityHome
import com.bnvs.metaler.termsagree.ActivityTermsAgree
import com.bnvs.metaler.util.DeviceInfo
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import java.text.SimpleDateFormat
import java.util.*

class ActivityLogin : AppCompatActivity() {

    private val TAG = "ActivityLogin"

    private lateinit var callback: SessionCallback
    private lateinit var tokenRepository: TokenRepository
    private lateinit var userRepository: UserRepository
    private lateinit var profileRepository: ProfileRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tokenRepository = TokenRepository(this@ActivityLogin)
        userRepository = UserRepository()
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
                    // Metaler 회원가입 여부 확인
                    Log.d(TAG, "카카오 아이디 : ${result!!.id}")
                    val kakao_id = result.id.toString()

                    // local 에 signin_token 존재하는지 확인
                    tokenRepository.getSigninToken(object :
                        TokenDataSource.LoadSigninTokenCallback {
                        override fun onTokenloaded(token: SigninToken) {
                            val signinToken = token.signin_token
                            // local 에 access_token 존재하는지 확인
                            tokenRepository.getAccessToken(object :
                                TokenDataSource.LoadAccessTokenCallback {
                                override fun onTokenloaded(token: AccessToken) {
                                    // access_Token 이 유효한지(발급받은지 24시간이 지나지 않은) 확인
                                    if (isTokenValid(token.valid_time)) {
                                        openHome()
                                    } else {
                                        login(kakao_id, signinToken)
                                    }
                                }

                                override fun onTokenNotExist() {
                                    login(kakao_id, signinToken)
                                }
                            })
                        }

                        override fun onTokenNotExist() {
                            // signin_token 존재하지 않음, 회원가입 여부확인 api 호출
                            userRepository.checkMembership(
                                CheckMembershipRequest(kakao_id),
                                object : UserDataSource.CheckMembershipCallback {
                                    override fun onMembershipChecked(response: CheckMembershipResponse) {
                                        when (response.message) {
                                            "you_can_join" -> {
                                                openTermsAgree(
                                                    AddUserRequest(
                                                        kakao_id,
                                                        result.properties["nickname"].toString(),
                                                        result.properties["profile_image"].toString(),
                                                        result.kakaoAccount.email,
                                                        result.kakaoAccount.birthday,
                                                        result.kakaoAccount.gender.toString(),
                                                        null,
                                                        null,
                                                        null
                                                    )
                                                )
                                            }
                                            else -> {
                                                tokenRepository.saveSigninToken(
                                                    SigninToken(response.signin_token)
                                                )
                                                login(kakao_id, response.signin_token)
                                            }
                                        }
                                    }

                                    override fun onResponseError(message: String) {
                                        Log.d(TAG, "회원가입 여부 확인 응답 response failed : $message")
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

    // access_token 유효시간과 현재시간을 비교하여
    // 아직 유효시간이 남았으면 true 를 반환
    private fun isTokenValid(validTime: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))

        val validTimeDate = dateFormat.parse(validTime)!!.time
        val now = Date(System.currentTimeMillis()).time

        val duration = { x: Long, y: Long -> x - y}

        return duration(validTimeDate, now) > 0
    }

    // access_token 유효시간(발급시간으로부터 24시간까지)을 계산하여 리턴하는 함수
    private fun getValidTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR"))
        val calendar = Calendar.getInstance().apply {
            time = Date(System.currentTimeMillis())
            add(Calendar.DATE, 1)
        }

        return dateFormat.format(calendar.time)
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
            object : UserDataSource.LoginCallback {
                override fun onLoginSuccess(response: LoginResponse) {
                    // 발급받은 access_token local 에 저장
                    tokenRepository.saveAccessToken(
                        AccessToken(response.access_token, getValidTime())
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

                override fun onResponseError(message: String) {
                    Log.d(TAG, "Metaler 로그인 api 응답 response failed : $message")
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

}