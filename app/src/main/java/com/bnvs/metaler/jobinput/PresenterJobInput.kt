package com.bnvs.metaler.jobinput

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import com.bnvs.metaler.data.profile.Profile
import com.bnvs.metaler.data.profile.source.ProfileRepository
import com.bnvs.metaler.data.token.AccessToken
import com.bnvs.metaler.data.token.SigninToken
import com.bnvs.metaler.data.token.source.TokenRepository
import com.bnvs.metaler.data.user.AddUserRequest
import com.bnvs.metaler.data.user.AddUserResponse
import com.bnvs.metaler.data.user.LoginRequest
import com.bnvs.metaler.data.user.LoginResponse
import com.bnvs.metaler.data.user.source.UserDataSource
import com.bnvs.metaler.data.user.source.UserRepository
import com.bnvs.metaler.util.DeviceInfo
import java.text.SimpleDateFormat
import java.util.*

class PresenterJobInput(
    private val context: Context,
    private val view: ContractJobInput.View
) : ContractJobInput.Presenter {

    private val TAG = "PresenterJobInput"

    private val userRepository = UserRepository()
    private val tokenRepository = TokenRepository(context)
    private val profileRepository = ProfileRepository(context)

    private lateinit var addUserRequest: AddUserRequest

    private lateinit var job: String
    private lateinit var job_type: String
    private lateinit var job_detail: String

    private var lastSelectedJobType = "null"

    override fun start() {

    }

    override fun getAddUserRequest(intent: Intent) {
        addUserRequest = intent.getSerializableExtra("addUserRequest") as AddUserRequest
    }

    override fun openStudent() {
        job = "student"
        view.showStudent()
    }

    override fun openExpert() {
        job = "expert"
        view.showExpert()
    }

    override fun openNothing() {
        job = "empty"
        view.showNothing()
    }

    override fun openCompany() {
        job_type = "company"
        lastSelectedJobType = "company"
        view.showCompany()
    }

    override fun openFounded() {
        job_type = "founded"
        lastSelectedJobType = "founded"
        view.showFounded()
    }

    override fun openFreelancer() {
        job_type = "freelancer"
        lastSelectedJobType = "null"
        view.showFreelancer()
    }

    override fun getSelectedJob(): String {
        return job
    }

    override fun getSelectedJobType(): String {
        return job_type
    }

    override fun getLastSelectedJobType(): String {
        return lastSelectedJobType
    }

    override fun completeJobInput(jobTypeInput: String?, jobDetailInput: String?) {
        when(job) {
            "student" -> {
                job_type = jobTypeInput!!
                job_detail = jobDetailInput!!
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                }else { addUser().also { showLog() } }
            }
            "expert" -> {
                when(job_type) {
                    "company" -> { job_detail = jobDetailInput!! }
                    "founded" -> { job_detail = jobDetailInput!! }
                    "freelancer" -> { job_detail = "empty" }
                }
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                }else { addUser().also { showLog() } }
            }
            "empty" -> {
                job_type = "empty"
                job_detail = "empty"
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                }else { addUser().also { showLog() } }
            }
            else -> { view.showEmptyTextDialog().also { showLog() } }
        }
    }

    override fun setUserRequest() {
        addUserRequest.job = job
        addUserRequest.job_type = job_type
        addUserRequest.job_detail = job_detail
        Log.d(TAG, "회원가입 요청 addUserRequest : $addUserRequest")
    }

    override fun addUser() {
        setUserRequest()
        userRepository.addUser(addUserRequest, object : UserDataSource.AddUserCallback {
            override fun onUserAdded(response: AddUserResponse) {
                view.showJoinCompleteDialog()
                tokenRepository.saveSigninToken(SigninToken(response.signin_token))
                val deviceInfo = DeviceInfo(context)
                userRepository.login(
                    LoginRequest(
                    addUserRequest.kakao_id,
                    response.signin_token,
                    "push_token",
                    deviceInfo.getDeviceId(),
                    deviceInfo.getDeviceModel(),
                    deviceInfo.getDeviceOs(),
                    deviceInfo.getAppVersion()), object : UserDataSource.LoginCallback {
                        override fun onLoginSuccess(response: LoginResponse) {
                            tokenRepository.saveAccessToken(
                                AccessToken(response.access_token, getValidTime())
                            )
                            profileRepository.saveProfile(
                                Profile(
                                    response.user.profile_nickname,
                                    response.user.profile_image_url,
                                    response.user.profile_email
                                )
                            )
                        }

                        override fun onResponseError(message: String) {
                            Log.d(TAG, "Metaler 로그인 api 응답 response failed : $message")
                        }

                        override fun onFailure(t: Throwable) {
                            Log.d(TAG, "로그인 실패 : $t")
                        }
                    })
                view.showHomeUi()
            }

            override fun onResponseError(message: String) {
                Log.d(TAG, "Metaler 회원가입 api 응답 response failed : $message")
            }

            override fun onFailure(t: Throwable) {
                Log.d(TAG, "회원가입 실패 : $t")
            }
        })
    }

    // editText 에서 공백없이 String 추출하는 함수
    override fun getString(editText: EditText): String {
        return editText.text.toString().replace(" ", "")
    }

    // editText 공백 확인 메서드
    private fun isEmptyText(text: String):Boolean {
        return TextUtils.isEmpty(text)
    }

    // job 로그 보여주기
    private fun showLog() {
        Log.d("JOB", "job: $job, job_type: $job_type, job_detail: $job_detail")
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
}