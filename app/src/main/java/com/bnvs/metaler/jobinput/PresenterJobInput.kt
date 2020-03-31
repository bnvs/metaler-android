package com.bnvs.metaler.jobinput

import android.content.res.Resources
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.content.res.ResourcesCompat
import com.bnvs.metaler.R
import kotlinx.android.synthetic.main.activity_job_input.*

class PresenterJobInput(
    private val view: ContractJobInput.View
) : ContractJobInput.Presenter {

    private lateinit var job: String
    private lateinit var job_type: String
    private lateinit var job_detail: String

    private var lastSelectedExpertJobType = "null"

    override fun start() {

    }

    override fun getAddUserRequest() {

    }

    override fun openStudent() {
        job = "student"
        view.showStudent()
    }

    override fun openExpert() {
        job = "expert"
        view.showExptert()
    }

    override fun openNothing() {
        job = "empty"
        view.showNothing()
    }

    override fun openCompany() {
        job_type = "company"
        lastSelectedExpertJobType = "company"
        view.showCompany()
    }

    override fun openFounded() {
        job_type = "founded"
        lastSelectedExpertJobType = "founded"
        view.showFounded()
    }

    override fun openFreelancer() {
        job_type = "freelancer"
        lastSelectedExpertJobType = "null"
        view.showFreelancer()
    }

    override fun setButtonEnabled(button: TextView, b: Boolean) {
        if (b) {
            button.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            button.setTextColor(ResourcesCompat.getColor(Resources.getSystem() , R.color.colorPurple, null))
        }else {
            button.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            button.setTextColor(ResourcesCompat.getColor(Resources.getSystem() , R.color.colorLightGrey, null))
        }
    }

    override fun onButtonChanged(clickedButton: TextView, buttons: List<TextView>) {
        for (button in buttons) {
            if (button == clickedButton) {
                setButtonEnabled(button, true)
            }else setButtonEnabled(button, false)
        }
    }

    override fun onGroupChanged(groupToShow: Group?, groups: List<Group>) {
        for (group in groups) {
            if (group == groupToShow) {
                group.visibility = View.VISIBLE
            }else {
                group.visibility = View.GONE
            }
        }
    }

    override fun getSelectedJob(): String {
        return job
    }

    override fun getSelectedJobType(): String {
        return job_type
    }

    override fun getlastSelectedExpertJobType(): String {
        return lastSelectedExpertJobType
    }

    override fun completeJobInput(jobTypeInput: String?, jobDetailInput: String?) {
        when(job) {
            "student" -> {
                job_type = jobTypeInput!!
                job_detail = jobDetailInput!!
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog()
                    showLog()
                }else { showLog() }
            }
            "expert" -> {
                when(job_type) {
                    "company" -> { job_detail = jobDetailInput!! }
                    "founded" -> { job_detail = jobDetailInput!! }
                    "freelancer" -> { job_detail = "empty" }
                }
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog()
                    showLog()
                }else { showLog() }
            }
            "empty" -> {
                job_type = "empty"
                job_detail = "empty"
                if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                    view.showEmptyTextDialog()
                    showLog()
                }else { showLog() }
            }
            else -> {
                view.showEmptyTextDialog()
                showLog() }
        }
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
}