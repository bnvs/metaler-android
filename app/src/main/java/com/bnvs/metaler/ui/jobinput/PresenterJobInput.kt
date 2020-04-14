package com.bnvs.metaler.ui.jobinput

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
import com.bnvs.metaler.data.user.certification.model.AddUserRequest
import com.bnvs.metaler.data.user.certification.model.AddUserResponse
import com.bnvs.metaler.data.user.certification.model.LoginRequest
import com.bnvs.metaler.data.user.certification.model.LoginResponse
import com.bnvs.metaler.data.user.certification.source.UserCertificationDataSource
import com.bnvs.metaler.data.user.certification.source.UserCertificationRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.DeviceInfo
import retrofit2.HttpException

class PresenterJobInput(
    private val context: Context,
    private val view: ContractJobInput.View
) : ContractJobInput.Presenter {

    private val TAG = "PresenterJobInput"

    private val userRepository = UserCertificationRepository()
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
        when (job) {
            "student" -> {
                job_type = jobTypeInput!!
                job_detail = jobDetailInput!!
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                } else {
                    addUser().also { showLog() }
                }
            }
            "expert" -> {
                when (job_type) {
                    "company" -> {
                        job_detail = jobDetailInput!!
                    }
                    "founded" -> {
                        job_detail = jobDetailInput!!
                    }
                    "freelancer" -> {
                        job_detail = "empty"
                    }
                }
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                } else {
                    addUser().also { showLog() }
                }
            }
            "empty" -> {
                job_type = "empty"
                job_detail = "empty"
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                } else {
                    addUser().also { showLog() }
                }
            }
            else -> {
                view.showEmptyTextDialog().also { showLog() }
            }
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
        userRepository.addUser(
            addUserRequest,
            object : UserCertificationDataSource.AddUserCallback {
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
                            deviceInfo.getAppVersion()
                        ), object : UserCertificationDataSource.LoginCallback {
                            override fun onLoginSuccess(response: LoginResponse) {
                                tokenRepository.saveAccessToken(
                                    AccessToken(response.access_token)
                                )
                                profileRepository.saveProfile(
                                    Profile(
                                        response.user.profile_nickname,
                                        response.user.profile_image_url,
                                        response.user.profile_email
                                    )
                                )
                            }

                            override fun onResponseError(exception: HttpException) {
                                val error =
                                    NetworkUtil.getErrorResponse(exception.response().errorBody()!!)
                                Log.d(TAG, "로그인 실패 : $error")
                            }

                            override fun onFailure(t: Throwable) {
                                Log.d(TAG, "로그인 실패 : $t")
                            }
                        })
                    view.showHomeUi()
                }

                override fun onResponseError(exception: HttpException) {
                    val error =
                        NetworkUtil.getErrorResponse(exception.response().errorBody()!!)
                    Log.d(TAG, "회원가입 실패 : $error")
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
    private fun isEmptyText(text: String): Boolean {
        return TextUtils.isEmpty(text)
    }

    // job 로그 보여주기
    private fun showLog() {
        Log.d("JOB", "job: $job, job_type: $job_type, job_detail: $job_detail")
    }
}