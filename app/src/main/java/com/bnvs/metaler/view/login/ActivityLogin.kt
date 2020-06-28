package com.bnvs.metaler.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import com.bnvs.metaler.data.categories.source.repository.CategoriesRepository
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.data.token.model.AccessToken
import com.bnvs.metaler.data.token.model.SigninToken
import com.bnvs.metaler.data.token.source.repository.TokenRepository
import com.bnvs.metaler.data.user.certification.model.CheckMembershipRequest
import com.bnvs.metaler.data.user.certification.model.KakaoUserInfo
import com.bnvs.metaler.data.user.certification.model.LoginRequest
import com.bnvs.metaler.data.user.certification.model.User
import com.bnvs.metaler.data.user.certification.source.repository.UserCertificationRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.constants.NO_HEADER
import com.bnvs.metaler.util.constants.TOKEN_EXPIRED
import com.bnvs.metaler.view.home.ActivityHome
import com.bnvs.metaler.view.termsagree.ActivityTermsAgree
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject


class ActivityLogin : AppCompatActivity() {

    private val TAG = "로그인 액티비티"

    private lateinit var callback: SessionCallback

    private val tokenRepository: TokenRepository by inject()
    private val userRepository: UserCertificationRepository by inject()
    private val profileRepository: ProfileRepository by inject()
    private val categoriesRepository: CategoriesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setSplashView(true)
        setTransparentStatusBar()

        // SessionCallback 초기화
        callback = SessionCallback()
        // 현재 세션에 callback 붙이기
        Session.getCurrentSession().addCallback(callback)
        // 현재 앱에 유효한 카카오 로그인 토큰이 있다면 바로 로그인(자동 로그인과 유사)
        Session.getCurrentSession().checkAndImplicitOpen()

        // 세션이 닫혀있는 경우(사용자가 카카오 로그인을 시도해야하는 경우)
        if (Session.getCurrentSession().isClosed) {
            setSplashView(false)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        setSplashView(true)
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
                    if (result != null) {
                        getSigninToken(result)
                    } else {
                        makeToast(getString(R.string.NO_KAKAO_LOGIN_RESULT))
                    }
                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    // 로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                    makeToast(getString(R.string.SESSION_CLOSED) + errorResult.toString())
                }
            })
        }

        override fun onSessionOpenFailed(exception: KakaoException?) {
            // 로그인 세션이 정상적으로 열리지 않았을 때
            setSplashView(false)
            if (exception != null) {
                com.kakao.util.helper.log.Logger.e(exception)
                makeToast(getString(R.string.INTERNET_DISCONNECTED_LOGIN_ERROR) + exception)
            } else {
                makeToast(getString(R.string.INTERNET_DISCONNECTED_LOGIN_ERROR))
            }
        }
    }

    private fun saveSigninToken(token: String) {
        tokenRepository.saveSigninToken(SigninToken(token))
    }

    private fun saveAccessToken(token: String) {
        tokenRepository.saveAccessToken(AccessToken(token))
    }

    private fun saveProfileData(user: User) {
        profileRepository.saveUserInfo(user)
    }

    private fun getSigninToken(result: MeV2Response) {
        tokenRepository.getSigninToken(
            onTokenLoaded = { token ->
                login(result.id.toString(), token.signin_token)
            },
            onTokenNotExist = {
                checkMembership(result)
            }
        )
    }

    private fun checkMembership(result: MeV2Response) {
        val kakaoId = result.id.toString()
        val kakaoNickname = result.properties["nickname"].toString()
        val kakaoProfileImage = result.properties["profile_image"].toString()
        val kakaoEmail = result.kakaoAccount.email ?: null
        val kakaoGender = makeGenderText(result.kakaoAccount.gender?.toString() ?: "")

        userRepository.checkMembership(
            CheckMembershipRequest(kakaoId),
            onSuccess = { response ->
                when (response.message) {
                    getString(R.string.SIGN_UP) -> {
                        saveKakaoLoginResult(
                            KakaoUserInfo(
                                kakaoId,
                                kakaoNickname,
                                kakaoProfileImage,
                                kakaoEmail,
                                kakaoGender
                            )
                        )
                        openTermsAgree()
                    }
                    getString(R.string.LOGIN) -> {
                        saveSigninToken(response.signin_token)
                        login(kakaoId, response.signin_token)
                    }
                    else -> {
                        makeToast(getString(R.string.MEMBERSHIP_CHECK_ERROR) + response.message)
                    }
                }
            },
            onFailure = { e ->
                makeErrorToast(getString(R.string.MEMBERSHIP_CHECK_ERROR), e)
            },
            handleError = { e ->
                handleError(e)
            }
        )
    }

    // login api 요청 request body 반환하는 함수
    private fun loginRequest(kakao_id: String, signin_token: String): LoginRequest {
        val deviceInfo = userRepository.getDeviceInfo()
        val loginRequest = LoginRequest(
            kakao_id,
            signin_token,
            getPushToken(),
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
                NetworkUtil.setAccessToken(response.access_token)
                getCategoriesData()
                saveAccessToken(response.access_token)
                saveProfileData(response.user)
                openHome()
            },
            onFailure = { e ->
                makeErrorToast(getString(R.string.LOGIN_ERROR), e)
            },
            handleError = { e ->
                handleError(e)
            }
        )
    }

    // 최초화면 (로그인 액티비티) 에서 카테고리 데이터 가져옴
    private fun getCategoriesData() {
        categoriesRepository.getCategoriesFromRemote(
            onSuccess = { response ->
                categoriesRepository.saveCategories(response)
            },
            onFailure = { e ->
                makeErrorToast("카테고리 가져오기 실패", e)
            },
            handleError = { e ->
                handleError(e)
            }
        )
    }

    private fun getPushToken(): String {
        return "push_token"
    }

    private fun openTermsAgree() {
        val intent = Intent(this, ActivityTermsAgree::class.java)
        startActivity(intent)
        finish()
    }

    private fun openHome() {
        val intent = Intent(this, ActivityHome::class.java)
        startActivity(intent)
        finish()
    }

    private fun makeErrorToast(message: String, e: Throwable) {
        makeToast(message + NetworkUtil.getErrorMessage(e))
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityLogin, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveKakaoLoginResult(result: KakaoUserInfo) {
        userRepository.saveKakaoUserInfo(result)
    }

    private fun makeGenderText(kakaoGender: String): String? {
        return when (kakaoGender) {
            "MALE" -> "M"
            "FEMALE" -> "W"
            else -> null
        }
    }

    private fun handleError(e: Int) {
        when (e) {
            NO_HEADER -> setAuthorizationHeader()
            TOKEN_EXPIRED -> deleteLocalTokens()
        }
    }

    private fun setAuthorizationHeader() {
        tokenRepository.getAccessToken(
            onTokenLoaded = { token ->
                NetworkUtil.setAccessToken(token.access_token)
            },
            onTokenNotExist = {
                finishAffinity()
                Intent(this, ActivityLogin::class.java).also {
                    startActivity(it)
                }
            }
        )
    }

    private fun deleteLocalTokens() {
        tokenRepository.deleteTokens()
        finishAffinity()
        Intent(this, ActivityLogin::class.java).also {
            startActivity(it)
        }
    }

    private fun setSplashView(b: Boolean) {
        if (b) {
            splash.visibility = View.VISIBLE
            login.visibility = View.GONE
        } else {
            splash.visibility = View.GONE
            login.visibility = View.VISIBLE
        }
    }

    // 상태 바를 투명하게 하고, padding 을 조절한다
    private fun setTransparentStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //현재 액티비티 레이아웃의 기준이 되는 splash 상태바 높이 만큼 top padding 을 줌 .
        splash.setPadding(0, statusBarHeight(this), 0, 0)
        Log.d(TAG, "상태바 높이? : ${statusBarHeight(this)}")

        //소프트키 올라온 높이만큼 전체 레이아웃 하단에 padding을 줌.
        splash.setPadding(0, 0, 0, softMenuHeight(this))
    }

    //상태바 높이 계산
    private fun statusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId)
        else 0
    }

    //하단 소프트키 높이 구함
    private fun softMenuHeight(context: Context): Int {
        val resourceId =
            context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else 0
    }
}