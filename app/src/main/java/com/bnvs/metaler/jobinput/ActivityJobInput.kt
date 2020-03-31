package com.bnvs.metaler.jobinput

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bnvs.metaler.R
import kotlinx.android.synthetic.main.activity_job_input.*
import kotlinx.android.synthetic.main.activity_job_input.expertBtn
import kotlinx.android.synthetic.main.activity_job_input.expertGroup
import kotlinx.android.synthetic.main.activity_job_input.nothingBtn
import kotlinx.android.synthetic.main.activity_job_input.studentBtn
import kotlinx.android.synthetic.main.activity_job_input.studentGroup

class ActivityJobInput : AppCompatActivity() {

    val TAG = "ActivityJobInput"

    private lateinit var job: String
    private lateinit var job_type: String
    private lateinit var job_detail: String
    
    private var lastSelectedExpertJobType = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_input)

        initClickListeners()
    }

    // 클릭리스너 초기화
    private fun initClickListeners() {
        setFirstCategoryButtons()
        setJobTypeButtons()
        setCompleteButton()
    }

    // 대분류 버튼 클릭 리스너
    private fun setFirstCategoryButtons() {
        val firstCategoryButtons = listOf(studentBtn, expertBtn, nothingBtn)

        studentBtn.setOnClickListener {
            job = "student"
            onButtonChanged(studentBtn, firstCategoryButtons)
            showCompleteButton()
            studentGroup.visibility = View.VISIBLE
            expertGroup.visibility = View.GONE

            companyGroup.visibility = View.GONE
            shopOwnerGroup.visibility = View.GONE
        }
        expertBtn.setOnClickListener {
            job = "expert"
            onButtonChanged(expertBtn, firstCategoryButtons)
            showCompleteButton()
            studentGroup.visibility = View.GONE
            expertGroup.visibility = View.VISIBLE

            if (lastSelectedExpertJobType == "company") {
                companyGroup.visibility = View.VISIBLE
            }else if (lastSelectedExpertJobType == "founded") {
                shopOwnerGroup.visibility = View.VISIBLE
            }
        }
        nothingBtn.setOnClickListener {
            job = "empty"
            onButtonChanged(nothingBtn, firstCategoryButtons)
            showCompleteButton()
            studentGroup.visibility = View.GONE
            expertGroup.visibility = View.GONE

            companyGroup.visibility = View.GONE
            shopOwnerGroup.visibility = View.GONE
        }

    }

    // 전문가 업무형태 클릭 리스너
    private fun setJobTypeButtons() {
        val jobTypeButtons = listOf(companyBtn, shopOwnerBtn, freelancerBtn)

        companyBtn.setOnClickListener {
            job_type = "company"
            onButtonChanged(companyBtn, jobTypeButtons)
            companyGroup.visibility = View.VISIBLE
            shopOwnerGroup.visibility = View.GONE

            lastSelectedExpertJobType = "company"
        }

        shopOwnerBtn.setOnClickListener {
            job_type = "founded"
            onButtonChanged(shopOwnerBtn, jobTypeButtons)
            companyGroup.visibility = View.GONE
            shopOwnerGroup.visibility = View.VISIBLE

            lastSelectedExpertJobType = "founded"
        }

        freelancerBtn.setOnClickListener {
            job_type = "freelancer"
            onButtonChanged(freelancerBtn, jobTypeButtons)
            companyGroup.visibility = View.GONE
            shopOwnerGroup.visibility = View.GONE

            lastSelectedExpertJobType = "null"
        }

    }

    // 버튼 누르면 버튼들 색깔 바뀌게 하기
    private fun onButtonChanged(clickedButton: TextView, buttons: List<TextView>) {
        for (button in buttons) {
            if (clickedButton == button) {
                setButtonEnabled(button, true)
            }else setButtonEnabled(button, false)
        }
    }

    // 버튼 배경 바꾸기
    private fun setButtonEnabled(button: TextView, b: Boolean) {
        if (b) {
            button.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            button.setTextColor(ContextCompat.getColor(this ,R.color.colorPurple))
        }else {
            button.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            button.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))
        }
    }

    // 완료 버튼 보여주기
    private fun showCompleteButton() {
        completeBtn.visibility = View.VISIBLE
    }


    // 완료 버튼 클릭 리스너
    private fun setCompleteButton() {
        completeBtn.setOnClickListener {
            when(job) {
                "student" -> {
                    job_type = getString(universityNameInput)
                    job_detail = getString(majorNameInput)
                    if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                        showEmptyTextDialog()
                        showLog()
                    }else { showLog() }
                }
                "expert" -> {
                    when(job_type) {
                        "company" -> { job_detail = getString(companyNameInput) }
                        "founded" -> { job_detail = getString(shopNameInput) }
                        "freelancer" -> { job_detail = "empty" }
                    }
                    if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                        showEmptyTextDialog()
                        showLog()
                    }else { showLog() }
                }
                "empty" -> {
                    job_type = "empty"
                    job_detail = "empty"
                    if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                        showEmptyTextDialog()
                        showLog()
                    }else { showLog() }
                }
                else -> {
                    showEmptyTextDialog()
                    showLog() }
            }
        }
    }

    // job 로그 보여주기
    private fun showLog() {
        Log.d(TAG, "job: $job, job_type: $job_type, job_detail: $job_detail")
    }

    // editText 에서 공백없이 String 추출하는 함수
    private fun getString(editText: EditText): String {
        return editText.text.toString().replace(" ", "")
    }

    // editText 공백 확인 메서드
    private fun isEmptyText(text: String):Boolean {
        return TextUtils.isEmpty(text)
    }

    // 직업 입력에 공백이 있을 시 띄우는 다이얼로그
    private fun showEmptyTextDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("알림")
                .setMessage("소속 입력을 완료해주세요")
                .show()
        }
    }


}
