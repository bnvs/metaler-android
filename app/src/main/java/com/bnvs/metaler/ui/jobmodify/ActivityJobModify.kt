package com.bnvs.metaler.ui.jobmodify

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import com.bnvs.metaler.R
import kotlinx.android.synthetic.main.activity_job_modify.*

class ActivityJobModify : AppCompatActivity(), ContractJobModify.View {

    override lateinit var presenter: ContractJobModify.Presenter

    private lateinit var firstCategoryButtons: List<TextView>
    private lateinit var firstCategoryGroups: List<Group>
    private lateinit var jobTypeButtons: List<TextView>
    private lateinit var jobTypeGroups: List<Group>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_modify)

        firstCategoryButtons = listOf(studentBtn, expertBtn, nothingBtn)
        firstCategoryGroups = listOf(studentGroup, expertGroup)
        jobTypeButtons = listOf(companyBtn, shopOwnerBtn, freelancerBtn)
        jobTypeGroups = listOf(companyGroup, shopOwnerGroup)

        // Create the presenter
        presenter = PresenterJobModify(this)

        // Set up Buttons
        initClickListeners()

        presenter.apply {
            start()
        }

    }

    override fun showStudent() {
        onButtonChanged(studentBtn, firstCategoryButtons)
        onGroupChanged(studentGroup, firstCategoryGroups)
        onGroupChanged(null, jobTypeGroups)
    }

    override fun showExpert(jobType: String) {
        onButtonChanged(expertBtn, firstCategoryButtons)
        onGroupChanged(expertGroup, firstCategoryGroups)
        when (jobType) {
            "company" -> onGroupChanged(companyGroup, jobTypeGroups)
            "founded" -> onGroupChanged(shopOwnerGroup, jobTypeGroups)
            else -> onGroupChanged(null, jobTypeGroups)
        }
    }

    override fun showNothing() {
        onButtonChanged(nothingBtn, firstCategoryButtons)
        onGroupChanged(null, firstCategoryGroups)
        onGroupChanged(null, jobTypeGroups)
    }

    override fun showCompany() {
        onButtonChanged(companyBtn, jobTypeButtons)
        onGroupChanged(companyGroup, jobTypeGroups)
    }

    override fun showFounded() {
        onButtonChanged(shopOwnerBtn, jobTypeButtons)
        onGroupChanged(shopOwnerGroup, jobTypeGroups)
    }

    override fun showFreelancer() {
        onButtonChanged(freelancerBtn, jobTypeButtons)
        onGroupChanged(null, jobTypeGroups)
    }

    override fun showEmptyTextDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.job_input_alert))
                .setMessage(getString(R.string.job_input_guide))
                .setPositiveButton("확인") { _, _ ->
                }
                .show()
        }
    }

    override fun setStudent(jobType: String, jobDetail: String) {
        universityNameInput.setText(jobType)
        majorNameInput.setText(jobDetail)
    }

    override fun setCompany(jobDetail: String) {
        companyNameInput.setText(jobDetail)
    }

    override fun setFounded(jobDetail: String) {
        shopNameInput.setText(jobDetail)
    }

    override fun showJobModifyCompleteDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.job_input_alert))
                .setMessage("소속 변경이 완료되었습니다")
                .setPositiveButton("확인") { _, _ ->
                }
                .show()
        }
    }

    override fun showErrorMessage(errorMessage: String) {
        makeToast(errorMessage)
    }

    private fun initClickListeners() {
        setTitleBarButton()
        setFirstCategoryButtons()
        setJobTypeButtons()
        setCompleteButton()
    }

    private fun setTitleBarButton() {
        backBtn.setOnClickListener { finish() }
    }

    // 대분류 버튼 클릭 리스너
    private fun setFirstCategoryButtons() {
        studentBtn.setOnClickListener { presenter.openStudent() }
        expertBtn.setOnClickListener { presenter.openExpert() }
        nothingBtn.setOnClickListener { presenter.openNothing() }
    }

    // 전문가 업무형태 클릭 리스너
    private fun setJobTypeButtons() {
        companyBtn.setOnClickListener { presenter.openCompany() }
        shopOwnerBtn.setOnClickListener { presenter.openFounded() }
        freelancerBtn.setOnClickListener { presenter.openFreelancer() }
    }

    private fun setCompleteButton() {
        completeBtn.setOnClickListener {
            when (presenter.getSelectedJob()) {
                "student" -> {
                    presenter.completeJobInput(
                        presenter.getString(universityNameInput),
                        presenter.getString(majorNameInput)
                    )
                }
                "expert" -> {
                    when (presenter.getSelectedJobType()) {
                        "company" -> {
                            presenter.completeJobInput(
                                "company",
                                presenter.getString(companyNameInput)
                            )
                        }
                        "founded" -> {
                            presenter.completeJobInput(
                                "founded",
                                presenter.getString(shopNameInput)
                            )
                        }
                        "freelancer" -> {
                            presenter.completeJobInput(
                                "freelancer",
                                "empty"
                            )
                        }
                        else -> {
                            Log.d("ActivityJobModify", "소속 입력완료 에러")
                            showEmptyTextDialog()
                        }
                    }
                }
                "empty" -> {
                    presenter.completeJobInput(
                        "empty",
                        "empty"
                    )
                }
                else -> {
                    Log.d("ActivityJobModify", "소속 입력완료 에러")
                    showEmptyTextDialog()
                }
            }
        }
    }

    override fun hideSoftInput() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(universityNameInput.windowToken, 0)
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityJobModify, message, Toast.LENGTH_LONG).show()
    }

    // enabled = true / false 에 따라 버튼 색상이 바뀜
    private fun setButtonEnabled(button: TextView, b: Boolean) {
        if (b) {
            button.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            button.setTextColor(ContextCompat.getColor(this, R.color.colorPurple))
        } else {
            button.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            button.setTextColor(ContextCompat.getColor(this, R.color.colorLightGrey))
        }
    }

    // 원하는 버튼만 enabled 상태로 바꿔주는 함수
    private fun onButtonChanged(clickedButton: TextView, buttons: List<TextView>) {
        for (button in buttons) {
            if (button == clickedButton) {
                setButtonEnabled(button, true)
            } else setButtonEnabled(button, false)
        }
    }

    // 원하는 소속입력 그룹만 보여주는 함수
    private fun onGroupChanged(groupToShow: Group?, groups: List<Group>) {
        for (group in groups) {
            if (group == groupToShow) {
                group.visibility = View.VISIBLE
            } else {
                group.visibility = View.GONE
            }
        }
    }
}
