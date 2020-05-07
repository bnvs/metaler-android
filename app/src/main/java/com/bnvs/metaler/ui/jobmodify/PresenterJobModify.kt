package com.bnvs.metaler.ui.jobmodify

import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.source.UserModificationRepository
import com.bnvs.metaler.network.NetworkUtil

class PresenterJobModify(
    private val view: ContractJobModify.View
) : ContractJobModify.Presenter {

    private val userRepository = UserModificationRepository()

    private lateinit var originalJob: Job

    private var job = ""
    private var job_type = ""
    private var job_detail = ""

    override fun start() {
        getUserJob()
    }

    override fun getUserJob() {
        userRepository.getUserJob(
            onSuccess = { response ->
                originalJob = response
                this.job = response.job
                this.job_type = response.job_type
                this.job_detail = response.job_detail
                setUserJob()
            },
            onFailure = { e ->
                view.showErrorMessage(NetworkUtil.getErrorMessage(e))
            }
        )
    }

    override fun setUserJob() {
        when (job) {
            "student" -> {
                view.run {
                    showStudent()
                    setStudent(job_type, job_detail)
                }
            }
            "expert" -> {
                when (job_type) {
                    "company" -> {
                        view.run {
                            showExpert(job_type)
                            showCompany()
                            setCompany(job_detail)
                        }
                    }
                    "founded" -> {
                        view.run {
                            showExpert(job_type)
                            showFounded()
                            setFounded(job_detail)
                        }
                    }
                    "freelancer" -> {
                        view.run {
                            showExpert(job_type)
                            showFreelancer()
                        }
                    }
                    else -> view.showErrorMessage("올바른 직업 형식이 아닙니다")
                }
            }
            "empty" -> {
                view.showNothing()
            }
            else -> {
                view.showErrorMessage("올바른 직업 형식이 아닙니다")
            }
        }
    }

    override fun completeJobInput(jobTypeInput: String, jobDetailInput: String) {
        job_type = jobTypeInput
        job_detail = jobDetailInput

        when (job) {
            "student" -> {
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                } else {
                    if (isUserJobChanged()) {
                        modifyUserJob().also { showLog() }
                    } else {
                        view.showModifyTheJobDialog()
                    }
                }
            }
            "expert" -> {
                if (isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                } else {
                    if (isUserJobChanged()) {
                        modifyUserJob().also { showLog() }
                    } else {
                        view.showModifyTheJobDialog()
                    }
                }
            }
            "empty" -> {
                if (isUserJobChanged()) {
                    modifyUserJob().also { showLog() }
                } else {
                    view.showModifyTheJobDialog()
                }
            }
            else -> {
                view.showEmptyTextDialog().also { showLog() }
            }
        }
    }

    private fun isUserJobChanged(): Boolean {
        return !(job == originalJob.job
                && job_type == originalJob.job_type
                && job_detail == originalJob.job_detail)
    }

    override fun modifyUserJob() {
        userRepository.modifyUserJob(
            Job(job, job_type, job_detail),
            onSuccess = {
                view.run {
                    originalJob = Job(job, job_type, job_detail)
                    showJobModifyCompleteDialog()
                    hideSoftInput()
                }
            },
            onFailure = { e ->
                view.run {
                    showErrorMessage(NetworkUtil.getErrorMessage(e))
                    hideSoftInput()
                }
            }
        )
    }

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

    override fun openStudent() {
        job = "student"
        view.showStudent()
    }

    override fun openExpert() {
        job = "expert"
        view.showExpert(job_type)
    }

    override fun openNothing() {
        job = "empty"
        view.showNothing()
    }

    override fun openCompany() {
        job_type = "company"
        view.showCompany()
    }

    override fun openFounded() {
        job_type = "founded"
        view.showFounded()
    }

    override fun openFreelancer() {
        job_type = "freelancer"
        view.showFreelancer()
    }

    override fun getSelectedJob(): String {
        return job
    }

    override fun getSelectedJobType(): String {
        return job_type
    }
}