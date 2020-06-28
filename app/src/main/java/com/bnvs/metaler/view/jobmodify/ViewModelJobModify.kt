package com.bnvs.metaler.view.jobmodify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.source.repository.UserModificationRepository
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.base.BaseViewModel

class ViewModelJobModify(
    private val userRepository: UserModificationRepository
) : BaseViewModel() {

    // original
    private lateinit var originalJob: Job

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

    private val _finishThisActivity = MutableLiveData<Boolean>().apply { value = false }
    val finishThisActivity: LiveData<Boolean> = _finishThisActivity

    init {
        loadUserJob()
    }

    private fun loadUserJob() {
        userRepository.getUserJob(
            onSuccess = { response ->
                originalJob = response
                setOriginalJob()
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    private fun modifyUserJob() {
        userRepository.modifyUserJob(
            getJobRequest(),
            onSuccess = {
                _errorDialogMessage.setMessage("소속 변경이 완료되었습니다")
            },
            onFailure = { e ->
                _errorToastMessage.setMessage(NetworkUtil.getErrorMessage(e))
            },
            handleError = { e -> _errorCode.setErrorCode(e) }
        )
    }

    private fun setOriginalJob() {
        originalJob.job.let {
            _job.value = it
            onJobChanged(it)
        }
        originalJob.job_type.let {
            jobType.value = it
            when (originalJob.job) {
                "student" -> studentJobType.value = it
                "expert" -> onJobTypeChanged(it)
            }
        }
        originalJob.job_detail.let {
            jobDetail.value = it
            when (originalJob.job) {
                "student" -> studentJobDetail.value = it
                "expert" -> {
                    when (originalJob.job_type) {
                        "company" -> companyJobDetail.value = it
                        "founded" -> foundedJobDetail.value = it
                    }
                }
            }
        }
    }


    private fun getJobRequest(): Job {
        return Job(_job.value ?: "", jobType.value ?: "", jobDetail.value ?: "")
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

    fun completeJobModify() {
        if (!isUserJobChanged()) {
            return
        }
        if (validateInput()) {
            modifyUserJob()
        } else {
            _errorDialogMessage.setMessage("소속 입력을 완료해주세요")
        }
    }

    private fun isUserJobChanged(): Boolean {
        return !(job.value == originalJob.job
                && jobType.value == originalJob.job_type
                && jobDetail.value == originalJob.job_detail)
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
                    else -> return false

                }
            }
            "empty" -> {
                jobType.value = "empty"
                jobDetail.value = "empty"
                return true
            }
            else -> return false
        }

    }

    private fun removeTextSpace(original: String): String {
        return original.trim()
    }

    fun finishJobModifyActivity() {
        _finishThisActivity.enable()
    }

}