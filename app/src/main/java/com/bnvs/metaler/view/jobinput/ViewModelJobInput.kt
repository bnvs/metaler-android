package com.bnvs.metaler.view.jobinput

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.profile.source.repository.ProfileRepository
import com.bnvs.metaler.data.token.model.AccessToken
import com.bnvs.metaler.data.token.model.SigninToken
import com.bnvs.metaler.data.token.source.repository.TokenRepository
import com.bnvs.metaler.data.user.certification.model.AddUserRequest
import com.bnvs.metaler.data.user.certification.model.KakaoUserInfo
import com.bnvs.metaler.data.user.certification.model.LoginRequest
import com.bnvs.metaler.data.user.certification.model.User
import com.bnvs.metaler.data.user.certification.source.repository.UserCertificationRepository
import com.bnvs.metaler.data.user.modification.model.TermsAgreements
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.BaseViewModel

class ViewModelJobInput(
    private val userCertificationRepository: UserCertificationRepository,
    private val userModificationRepository: UserModificationRepository,
    private val tokenRepository: TokenRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel() {

    private val _backToTermsAgreeActivity = MutableLiveData<Boolean>().apply { value = false }
    val backToTermsAgreeActivity: LiveData<Boolean> = _backToTermsAgreeActivity
    private val _openHomeActivity = MutableLiveData<Boolean>().apply { value = false }
    val openHomeActivity: LiveData<Boolean> = _openHomeActivity

    private lateinit var kakaoUserInfo: KakaoUserInfo
    private lateinit var termsAgreements: TermsAgreements
    private lateinit var addUserRequest: AddUserRequest

    init {
        loadTermsAgreements()
    }

    // JobInputs
    private val _job = MutableLiveData<String>().apply { value = "" }
    val job: LiveData<String> = _job

    private val jobType = MutableLiveData<String>().apply { value = "" }
    private val jobDetail = MutableLiveData<String>().apply { value = "" }

    // EditText JobInputs
    val studentJobType = MutableLiveData<String>().apply { value = "" }
    val studentJobDetail = MutableLiveData<String>().apply { value = "" }
    val companyJobDetail = MutableLiveData<String>().apply { value = "" }
    val foundedJobDetail = MutableLiveData<String>().apply { value = "" }

    // JobInput Visibilities
    private val _jobs = MutableLiveData<Map<String, Boolean>>().apply {
        value = mapOf("student" to false, "expert" to false, "empty" to false)
    }
    val jobs: LiveData<Map<String, Boolean>> = _jobs

    private val _jobTypes = MutableLiveData<Map<String, Boolean>>().apply {
        value = mapOf("company" to false, "founded" to false, "freelancer" to false)
    }
    val jobTypes: LiveData<Map<String, Boolean>> = _jobTypes

    private fun loadTermsAgreements() {
        userModificationRepository.getTermsAgreements(
            onSuccess = { agreements ->
                termsAgreements = agreements
            },
            onFailure = {
                _errorToastMessage.setMessage("약관 동의 내역을 불러오는데 실패했습니다")
            }
        )
    }

    fun onJobChanged(jobInput: String) {
        when (jobInput) {
            "student" -> {
                _job.value = "student"
                _jobs.value = mapOf("student" to true, "expert" to false, "empty" to false)
                _jobTypes.value =
                    mapOf("company" to false, "founded" to false, "freelancer" to false)
            }
            "expert" -> {
                _job.value = "expert"
                _jobs.value = mapOf("student" to false, "expert" to true, "empty" to false)
                onJobTypeChanged(jobType.value ?: "")
            }
            "empty" -> {
                _job.value = "empty"
                _jobs.value = mapOf("student" to false, "expert" to false, "empty" to true)
                _jobTypes.value =
                    mapOf("company" to false, "founded" to false, "freelancer" to false)
            }
        }
    }

    fun onJobTypeChanged(jobTypeInput: String) {
        when (jobTypeInput) {
            "company" -> {
                jobType.value = "company"
                _jobTypes.value =
                    mapOf("company" to true, "founded" to false, "freelancer" to false)
            }
            "founded" -> {
                jobType.value = "founded"
                _jobTypes.value =
                    mapOf("company" to false, "founded" to true, "freelancer" to false)
            }
            "freelancer" -> {
                jobType.value = "freelancer"
                _jobTypes.value =
                    mapOf("company" to false, "founded" to false, "freelancer" to true)
            }
        }
    }

    fun completeJobInput() {
        if (validateInput()) {
            addUser()
        } else {
            _errorDialogMessage.setMessage("소속 입력을 완료해주세요")
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
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    private fun getKakaoUserInfo() {
        userCertificationRepository.getKakaoUserInfo(
            onSuccess = { response ->
                kakaoUserInfo = response
            },
            onFailure = {
                _errorToastMessage.setMessage("카카오 로그인 유저 정보에 문제가 있습니다.")
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
            "kakao",
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
                NetworkUtil.setAccessToken(response.access_token)
                saveAccessToken(response.access_token)
                saveUserInfo(response.user)
                startHomeActivity()
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    private fun loginRequest(signinToken: String): LoginRequest {
        val deviceInfo = userCertificationRepository.getDeviceInfo()
        return LoginRequest(
            kakaoUserInfo.kakao_id,
            "kakao",
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
        _backToTermsAgreeActivity.apply {
            value = true
            value = false
        }
    }

    private fun startHomeActivity() {
        _openHomeActivity.apply {
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
}