package com.bnvs.metaler.view.jobinput

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.profile.source.local.ProfileLocalDataSourceImpl
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.data.profile.source.repository.ProfileRepositoryImpl
import com.bnvs.metaler.data.token.model.AccessToken
import com.bnvs.metaler.data.token.model.SigninToken
import com.bnvs.metaler.data.token.source.local.TokenLocalDataSourceImpl
import com.bnvs.metaler.data.token.source.repository.TokenRepository
import com.bnvs.metaler.data.token.source.repository.TokenRepositoryImpl
import com.bnvs.metaler.data.user.certification.model.AddUserRequest
import com.bnvs.metaler.data.user.certification.model.KakaoUserInfo
import com.bnvs.metaler.data.user.certification.model.LoginRequest
import com.bnvs.metaler.data.user.certification.model.User
import com.bnvs.metaler.data.user.certification.source.local.UserCertificationLocalDataSourceImpl
import com.bnvs.metaler.data.user.certification.source.remote.UserCertificationRemoteDataSourceImpl
import com.bnvs.metaler.data.user.certification.source.repository.UserCertificationRepository
import com.bnvs.metaler.data.user.certification.source.repository.UserCertificationRepositoryImpl
import com.bnvs.metaler.data.user.modification.model.TermsAgreements
import com.bnvs.metaler.data.user.modification.source.local.UserModificationLocalDataSourceImpl
import com.bnvs.metaler.data.user.modification.source.remote.UserModificationRemoteDataSourceImpl
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepository
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepositoryImpl
import com.bnvs.metaler.network.NO_ERROR_TO_HANDLE
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.network.RetrofitClient
import com.bnvs.metaler.util.DeviceInfo

class ViewModelJobInput(application: Application) : AndroidViewModel(application) {

    private val userCertificationRepository: UserCertificationRepository =
        UserCertificationRepositoryImpl(
            UserCertificationLocalDataSourceImpl(application.applicationContext),
            UserCertificationRemoteDataSourceImpl()
        )

    private val userModificationRepository: UserModificationRepository =
        UserModificationRepositoryImpl(
            UserModificationLocalDataSourceImpl(application.applicationContext),
            UserModificationRemoteDataSourceImpl()
        )

    private val tokenRepository: TokenRepository =
        TokenRepositoryImpl(TokenLocalDataSourceImpl(application.applicationContext))

    private val profileRepository: ProfileRepository =
        ProfileRepositoryImpl(ProfileLocalDataSourceImpl(application.applicationContext))

    val errorToastMessage = MutableLiveData<String>().apply { value = "" }
    val errorDialogMessage = MutableLiveData<String>().apply { value = "" }
    val errorCode = MutableLiveData<Int>().apply { value = NO_ERROR_TO_HANDLE }

    val backToTermsAgreeActivity = MutableLiveData<Boolean>().apply { value = false }
    val openHomeActivity = MutableLiveData<Boolean>().apply { value = false }

    private lateinit var kakaoUserInfo: KakaoUserInfo
    private lateinit var termsAgreements: TermsAgreements
    private lateinit var addUserRequest: AddUserRequest

    init {
        getTermsAgreements()
    }

    // JobInputs
    val job = MutableLiveData<String>().apply { value = "" }
    private val jobType = MutableLiveData<String>().apply { value = "" }
    private val jobDetail = MutableLiveData<String>().apply { value = "" }

    // EditText JobInputs
    val studentJobType = MutableLiveData<String>().apply { value = "" }
    val studentJobDetail = MutableLiveData<String>().apply { value = "" }
    val companyJobDetail = MutableLiveData<String>().apply { value = "" }
    val foundedJobDetail = MutableLiveData<String>().apply { value = "" }

    // JobInput Visibilities
    val jobs = MutableLiveData<Map<String, Boolean>>().apply {
        value = mapOf("student" to false, "expert" to false, "empty" to false)
    }

    val jobTypes = MutableLiveData<Map<String, Boolean>>().apply {
        value = mapOf("company" to false, "founded" to false, "freelancer" to false)
    }

    private fun getTermsAgreements() {
        userModificationRepository.getTermsAgreements(
            onSuccess = { agreements ->
                termsAgreements = agreements
            },
            onFailure = {
                errorToastMessage.apply {
                    value = "약관 동의 내역을 불러오는데 실패했습니다"
                    value = clearStringValue()
                }
            }
        )
    }

    fun onJobChanged(jobInput: String) {
        when (jobInput) {
            "student" -> {
                job.value = "student"
                jobs.value = mapOf("student" to true, "expert" to false, "empty" to false)
                jobTypes.value =
                    mapOf("company" to false, "founded" to false, "freelancer" to false)
            }
            "expert" -> {
                job.value = "expert"
                jobs.value = mapOf("student" to false, "expert" to true, "empty" to false)
                onJobTypeChanged(jobType.value ?: "")
            }
            "empty" -> {
                job.value = "empty"
                jobs.value = mapOf("student" to false, "expert" to false, "empty" to true)
                jobTypes.value =
                    mapOf("company" to false, "founded" to false, "freelancer" to false)
            }
        }
    }

    fun onJobTypeChanged(jobTypeInput: String) {
        when (jobTypeInput) {
            "company" -> {
                jobType.value = "company"
                jobTypes.value = mapOf("company" to true, "founded" to false, "freelancer" to false)
            }
            "founded" -> {
                jobType.value = "founded"
                jobTypes.value = mapOf("company" to false, "founded" to true, "freelancer" to false)
            }
            "freelancer" -> {
                jobType.value = "freelancer"
                jobTypes.value = mapOf("company" to false, "founded" to false, "freelancer" to true)
            }
        }
    }

    fun completeJobInput() {
        if (validateInput()) {
            addUser()
        } else {
            errorDialogMessage.apply {
                value = "소속 입력을 완료해주세요"
                value = clearStringValue()
            }
        }
    }

    private fun addUser() {
        setAddUserRequest()
        userCertificationRepository.addUser(
            addUserRequest,
            onSuccess = { response ->
                deleteKakaoUserInfo()
                saveSigninToken(response.signin_token)
                login(loginRequest(response.signin_token))
            },
            onFailure = { e ->
                errorToastMessage.apply {
                    value = NetworkUtil.getErrorMessage(e)
                    value = clearStringValue()
                }
            },
            handleError = { e ->
                errorCode.apply {
                    value = e
                    value = NO_ERROR_TO_HANDLE
                }
            }
        )
    }

    private fun getKakaoUserInfo() {
        userCertificationRepository.getKakaoUserInfo(
            onSuccess = { response ->
                kakaoUserInfo = response
            },
            onFailure = {
                errorToastMessage.apply {
                    value = "카카오 로그인 유저 정보에 문제가 있습니다."
                    value = clearStringValue()
                }
            }
        )
    }

    private fun deleteKakaoUserInfo() {
        userCertificationRepository.deleteKakaoUserInfo()
    }

    private fun setAddUserRequest() {
        getKakaoUserInfo()

        val job = job.value ?: return
        val jobType = jobType.value ?: return
        val jobDetail = jobDetail.value ?: return
        val pushAllowChecked = termsAgreements.advertising_agree

        addUserRequest = AddUserRequest(
            kakaoUserInfo.kakao_id,
            kakaoUserInfo.profile_nickname,
            kakaoUserInfo.profile_image_url,
            kakaoUserInfo.profile_email,
            kakaoUserInfo.profile_gender,
            job,
            jobType,
            jobDetail,
            if (pushAllowChecked) 0 else 1
        )
    }

    private fun login(loginRequest: LoginRequest) {
        userCertificationRepository.login(
            loginRequest,
            onSuccess = { response ->
                RetrofitClient.setAccessToken(response.access_token)
                saveAccessToken(response.access_token)
                saveUserInfo(response.user)
                startHomeActivity()
            },
            onFailure = { e ->
                errorToastMessage.apply {
                    value = NetworkUtil.getErrorMessage(e)
                    value = clearStringValue()
                }
            },
            handleError = { e ->
                errorCode.apply {
                    value = e
                    value = NO_ERROR_TO_HANDLE
                }
            }
        )
    }

    private fun loginRequest(signinToken: String): LoginRequest {
        val deviceInfo = DeviceInfo(getApplication())
        return LoginRequest(
            kakaoUserInfo.kakao_id,
            signinToken,
            "push_token",
            deviceInfo.getDeviceId(),
            deviceInfo.getDeviceModel(),
            deviceInfo.getDeviceOs(),
            deviceInfo.getAppVersion()
        )
    }

    private fun validateInput(): Boolean {
        when (job.value) {
            "student" -> {
                return if (studentJobType.value.isNullOrBlank() || studentJobDetail.value.isNullOrBlank()) {
                    false
                } else {
                    jobType.value = removeTextSpace(studentJobType.value!!)
                    jobDetail.value = removeTextSpace(studentJobDetail.value!!)
                    true
                }
            }
            "expert" -> {
                when (jobType.value) {
                    "company" -> {
                        return if (companyJobDetail.value.isNullOrBlank()) {
                            false
                        } else {
                            jobDetail.value = removeTextSpace(companyJobDetail.value!!)
                            true
                        }
                    }
                    "founded" -> {
                        return if (foundedJobDetail.value.isNullOrBlank()) {
                            false
                        } else {
                            jobDetail.value = removeTextSpace(foundedJobDetail.value!!)
                            true
                        }
                    }
                    "freelancer" -> {
                        jobDetail.value = "empty"
                        return true
                    }
                    else -> {
                        return false
                    }
                }
            }
            "empty" -> {
                jobType.value = "empty"
                jobDetail.value = "empty"
                return true
            }
            else -> {
                return false
            }
        }
    }

    fun backToTermsAgreeActivity() {
        backToTermsAgreeActivity.apply {
            value = true
            value = false
        }
    }

    private fun startHomeActivity() {
        openHomeActivity.apply {
            value = true
            value = false
        }
    }

    private fun removeTextSpace(original: String): String {
        return original.trim()
    }

    private fun saveSigninToken(signinToken: String) {
        tokenRepository.saveSigninToken(SigninToken(signinToken))
    }

    private fun saveAccessToken(accessToken: String) {
        tokenRepository.saveAccessToken(AccessToken(accessToken))
    }

    private fun saveUserInfo(userInfo: User) {
        profileRepository.saveUserInfo(userInfo)
    }

    private fun clearStringValue(): String {
        return ""
    }
}