package com.bnvs.metaler.ui.jobinput

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.data.token.model.AccessToken
import com.bnvs.metaler.data.token.model.SigninToken
import com.bnvs.metaler.data.token.source.repository.TokenRepository
import com.bnvs.metaler.data.user.certification.model.AddUserRequest
import com.bnvs.metaler.data.user.certification.model.LoginRequest
import com.bnvs.metaler.data.user.certification.model.User
import com.bnvs.metaler.data.user.certification.source.repository.UserCertificationRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.network.RetrofitClient
import com.bnvs.metaler.util.DeviceInfo

class PresenterJobInput(
    private val context: Context,
    private val view: ContractJobInput.View
) : ContractJobInput.Presenter {

    private val TAG = "PresenterJobInput"

    private val userRepository =
        UserCertificationRepository()
    private val tokenRepository =
        TokenRepository(context)
    private val profileRepository =
        ProfileRepository(context)

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
        lastSelectedJobType = "freelancer"
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

    override fun completeJobInput(jobTypeInput: String, jobDetailInput: String) {
        job_type = jobTypeInput
        job_detail = jobDetailInput

        when (job) {
            "student" -> {
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                } else {
                    addUser().also { showLog() }
                }
            }
            "expert" -> {
                if (isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                } else {
                    addUser().also { showLog() }
                }
            }
            "empty" -> {
                addUser().also { showLog() }
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
            onSuccess = { response ->
                val signin_token = response.signin_token
                view.showJoinCompleteDialog()
                tokenRepository.saveSigninToken(
                    SigninToken(
                        signin_token
                    )
                )
                login(makeLoginRequest(signin_token))
            },
            onFailure = { e ->
                Log.d(TAG, "회원가입 실패 : ${NetworkUtil.getErrorMessage(e)}")
            }
        )
    }

    override fun login(request: LoginRequest) {
        userRepository.login(
            request,
            onSuccess = { response ->
                RetrofitClient.setAccessToken(response.access_token)
                saveAccessToken(response.access_token)
                saveProfileData(response.user)
                view.showHomeUi()
            },
            onFailure = { e ->
                Log.d(TAG, "로그인 실패 : ${NetworkUtil.getErrorMessage(e)}")
            }
        )
    }

    override fun makeLoginRequest(token: String): LoginRequest {
        val deviceInfo = DeviceInfo(context)
        return LoginRequest(
            addUserRequest.kakao_id,
            token,
            "push_token",
            deviceInfo.getDeviceId(),
            deviceInfo.getDeviceModel(),
            deviceInfo.getDeviceOs(),
            deviceInfo.getAppVersion()
        )
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
}