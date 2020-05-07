package com.bnvs.metaler.ui.jobmodify

import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import com.bnvs.metaler.data.user.modification.model.Job
import com.bnvs.metaler.data.user.modification.source.UserModificationRepository

class PresenterJobModify(
    private val view: ContractJobModify.View
) : ContractJobModify.Presenter {

    private val userRepository = UserModificationRepository()

    private var job = ""
    private var job_type = ""
    private var job_detail = ""

    override fun start() {
        getUserJob()
    }

    override fun getUserJob() {
        userRepository.getUserJob(
            onSuccess = {

            },
            onFailure = {

            }
        )
    }

    override fun completeJobInput(jobTypeInput: String, jobDetailInput: String) {
        job_type = jobTypeInput
        job_detail = jobDetailInput

        when (job) {
            "student" -> {
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                } else {
                    modifyUserJob().also { showLog() }
                }
            }
            "expert" -> {
                if (isEmptyText(job_detail)) {
                    view.showEmptyTextDialog().also { showLog() }
                } else {
                    modifyUserJob().also { showLog() }
                }
            }
            "empty" -> {
                modifyUserJob().also { showLog() }
            }
            else -> {
                view.showEmptyTextDialog().also { showLog() }
            }
        }
    }

    override fun modifyUserJob() {
        userRepository.modifyUserJob(
            Job(job, job_type, job_detail),
            onSuccess = {

            },
            onFailure = {

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openExpert() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openNothing() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openCompany() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openFounded() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openFreelancer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSelectedJob(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSelectedJobType(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLastSelectedJobType(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}