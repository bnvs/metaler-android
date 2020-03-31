package com.bnvs.metaler.jobinput

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import com.bnvs.metaler.R
import kotlinx.android.synthetic.main.activity_job_input.*
import kotlinx.android.synthetic.main.activity_job_input.expertBtn
import kotlinx.android.synthetic.main.activity_job_input.expertGroup
import kotlinx.android.synthetic.main.activity_job_input.nothingBtn
import kotlinx.android.synthetic.main.activity_job_input.studentBtn
import kotlinx.android.synthetic.main.activity_job_input.studentGroup

class ActivityJobInput : AppCompatActivity(), ContractJobInput.View {

    val TAG = "ActivityJobInput"

    override lateinit var presenter: ContractJobInput.Presenter

    private lateinit var firstCategoryButtons: List<TextView>
    private lateinit var firstCategoryGroups: List<Group>
    private lateinit var jobTypeButtons: List<TextView>
    private lateinit var jobTypeGroups: List<Group>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_input)

        firstCategoryButtons = listOf(studentBtn, expertBtn, nothingBtn)
        firstCategoryGroups = listOf(studentGroup, expertGroup)
        jobTypeButtons = listOf(companyBtn, shopOwnerBtn, freelancerBtn)
        jobTypeGroups = listOf(companyGroup, shopOwnerGroup)

        // Create the presenter
        presenter = PresenterJobInput(this)

        // Set up Buttons
        initClickListeners()
    }

    override fun showStudent() {
        onButtonChanged(studentBtn, firstCategoryButtons)
        showCompleteButton()
        onGroupChanged(studentGroup, firstCategoryGroups)
        onGroupChanged(null, jobTypeGroups)
    }

    override fun showExpert() {
        onButtonChanged(expertBtn, firstCategoryButtons)
        showCompleteButton()
        onGroupChanged(expertGroup, firstCategoryGroups)
        when {
            presenter.getLastSelectedJobType() == "company" -> onGroupChanged(companyGroup, jobTypeGroups)
            presenter.getLastSelectedJobType() == "founded" -> onGroupChanged(shopOwnerGroup, jobTypeGroups)
            else -> onGroupChanged(null, jobTypeGroups)
        }
    }

    override fun showNothing() {
        onButtonChanged(nothingBtn, firstCategoryButtons)
        showCompleteButton()
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

    override fun showCompleteButton() {
        completeBtn.visibility = View.VISIBLE
    }

    override fun showEmptyTextDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.job_input_alert))
                .setMessage(getString(R.string.job_input_guide))
                .show()
        }
    }

    override fun showJoinCompleteDialog() {
        Toast.makeText(this@ActivityJobInput, getString(R.string.job_input_complete), Toast.LENGTH_SHORT).show()
    }

    override fun showHomeUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // 클릭리스너 초기화
    private fun initClickListeners() {
        setFirstCategoryButtons()
        setJobTypeButtons()
        setCompleteButton()
    }

    // 대분류 버튼 클릭 리스너
    private fun setFirstCategoryButtons() {
        studentBtn.setOnClickListener {
            presenter.openStudent()
        }
        expertBtn.setOnClickListener {
            presenter.openExpert()
        }
        nothingBtn.setOnClickListener {
            presenter.openNothing()
        }
    }

    // 전문가 업무형태 클릭 리스너
    private fun setJobTypeButtons() {
        companyBtn.setOnClickListener {
            presenter.openCompany()
        }
        shopOwnerBtn.setOnClickListener {
            presenter.openFounded()
        }
        freelancerBtn.setOnClickListener {
            presenter.openFreelancer()
        }
    }

    // 소속 입력 완료 버튼 클릭 리스너
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
                                null,
                                presenter.getString(companyNameInput)
                            )
                        }
                        "founded" -> {
                            presenter.completeJobInput(
                                null,
                                presenter.getString(shopNameInput)
                            )
                        }
                        else -> {
                            presenter.completeJobInput(null, null)
                        }
                    }
                }
                else -> {
                    presenter.completeJobInput(null, null)
                }
            }
        }
    }

    // enabled = true / false 에 따라 버튼 색상이 바뀜
    private fun setButtonEnabled(button: TextView, b: Boolean) {
        if (b) {
            button.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            button.setTextColor(ContextCompat.getColor(this , R.color.colorPurple))
        }else {
            button.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            button.setTextColor(ContextCompat.getColor(this , R.color.colorLightGrey))
        }
    }

    // 원하는 버튼만 enabled 상태로 바꿔주는 함수
    private fun onButtonChanged(clickedButton: TextView, buttons: List<TextView>) {
        for (button in buttons) {
            if (button == clickedButton) {
                setButtonEnabled(button, true)
            }else setButtonEnabled(button, false)
        }
    }

    // 원하는 소속입력 그룹만 보여주는 함수
    private fun onGroupChanged(groupToShow: Group?, groups: List<Group>) {
        for (group in groups) {
            if (group == groupToShow) {
                group.visibility = View.VISIBLE
            }else {
                group.visibility = View.GONE
            }
        }
    }

}
