package com.bnvs.metaler.jobinput

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
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

    private var lastSelectedExpert = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_input)

        studentBtn.setOnClickListener {
            job = "student"
            studentBtn.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            studentBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorPurple))
            expertBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            expertBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))
            nothingBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            nothingBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))

            studentGroup.visibility = View.VISIBLE
            expertGroup.visibility = View.GONE

            companyGroup.visibility = View.GONE
            shopOwnerGroup.visibility = View.GONE
        }
        expertBtn.setOnClickListener {
            job = "expert"
            studentBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            studentBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))
            expertBtn.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            expertBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorPurple))
            nothingBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            nothingBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))

            studentGroup.visibility = View.GONE
            expertGroup.visibility = View.VISIBLE

            if (lastSelectedExpert == "company") {
                companyGroup.visibility = View.VISIBLE
            }else if (lastSelectedExpert == "founded") {
                shopOwnerGroup.visibility = View.VISIBLE
            }
        }
        nothingBtn.setOnClickListener {
            job = "empty"
            studentBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            studentBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))
            expertBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            expertBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))
            nothingBtn.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            nothingBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorPurple))

            studentGroup.visibility = View.GONE
            expertGroup.visibility = View.GONE

            companyGroup.visibility = View.GONE
            shopOwnerGroup.visibility = View.GONE
        }

        // 전문가 선택시 나오는 하위 그룹 클릭 리스너
        companyBtn.setOnClickListener {
            job_type = "company"
            companyBtn.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            companyBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorPurple))
            shopOwnerBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            shopOwnerBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))
            freelancerBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            freelancerBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))

            companyGroup.visibility = View.VISIBLE
            shopOwnerGroup.visibility = View.GONE

            lastSelectedExpert = "company"
        }
        shopOwnerBtn.setOnClickListener {
            job_type = "founded"
            companyBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            companyBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))
            shopOwnerBtn.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            shopOwnerBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorPurple))
            freelancerBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            freelancerBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))

            companyGroup.visibility = View.GONE
            shopOwnerGroup.visibility = View.VISIBLE

            lastSelectedExpert = "founded"
        }
        freelancerBtn.setOnClickListener {
            job_type = "freelancer"
            companyBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            companyBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))
            shopOwnerBtn.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            shopOwnerBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorLightGrey))
            freelancerBtn.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            freelancerBtn.setTextColor(ContextCompat.getColor(this ,R.color.colorPurple))

            companyGroup.visibility = View.GONE
            shopOwnerGroup.visibility = View.GONE

            lastSelectedExpert = "null"
        }

        completeBtn.setOnClickListener {
            when(job) {
                "student" -> {
                    job_type = universityNameInput.text.toString().replace(" ", "")
                    job_detail = majorNameInput.text.toString().replace(" ", "")
                    if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                        showEmptyTextDialog()
                        Log.d(TAG, "job: $job, job_type: $job_type, job_detail: $job_detail")
                    }else {
                        Log.d(TAG, "job: $job, job_type: $job_type, job_detail: $job_detail")
                    }
                }
                "expert" -> {
                    when(job_type) {
                        "company" -> { job_detail = companyNameInput.text.toString().replace(" ", "") }
                        "founded" -> { job_detail = shopNameInput.text.toString().replace(" ", "") }
                        "freelancer" -> { job_detail = "empty" }
                    }
                    if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                        showEmptyTextDialog()
                        Log.d(TAG, "job: $job, job_type: $job_type, job_detail: $job_detail")
                    }else {
                        Log.d(TAG, "job: $job, job_type: $job_type, job_detail: $job_detail")
                    }
                }
                "empty" -> {
                    job_type = "empty"
                    job_detail = "empty"
                    if (isEmptyText(job_type) || isEmptyText(job_detail)) {
                        showEmptyTextDialog()
                        Log.d(TAG, "job: $job, job_type: $job_type, job_detail: $job_detail")
                    }else {
                        Log.d(TAG, "job: $job, job_type: $job_type, job_detail: $job_detail")
                    }
                }
                else -> {
                    showEmptyTextDialog()
                    Log.d(TAG, "job: $job, job_type: $job_type, job_detail: $job_detail")
                }
            }
        }

    }

    // editText 공백 확인 메서드
    private fun isEmptyText(text: String):Boolean {
        return TextUtils.isEmpty(text)
    }

    private fun showEmptyTextDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("알림")
                .setMessage("소속 입력을 완료해주세요")
                .show()
        }
    }


}
