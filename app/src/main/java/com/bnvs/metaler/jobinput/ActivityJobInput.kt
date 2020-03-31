package com.bnvs.metaler.jobinput

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
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

    private val firstCategoryButtons = listOf(studentBtn, expertBtn, nothingBtn)
    private val firstCategoryGroups = listOf(studentGroup, expertGroup)
    private val jobTypeButtons = listOf(companyBtn, shopOwnerBtn, freelancerBtn)
    private val jobTypeGroups = listOf(companyGroup, shopOwnerGroup)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_input)

        // Create the presenter
        presenter = PresenterJobInput(this)

        // Set up Buttons
        initClickListeners()
    }

    override fun showStudent() {
        presenter.onButtonChanged(studentBtn, firstCategoryButtons)
        showCompleteButton()
        presenter.onGroupChanged(studentGroup, firstCategoryGroups)
        presenter.onGroupChanged(null, jobTypeGroups)
    }

    override fun showExptert() {
        presenter.onButtonChanged(expertBtn, firstCategoryButtons)
        showCompleteButton()
        presenter.onGroupChanged(expertGroup, firstCategoryGroups)
        if (presenter.getlastSelectedExpertJobType() == "company") {
            presenter.onGroupChanged(companyGroup, jobTypeGroups)
        } else if (presenter.getlastSelectedExpertJobType() == "founded") {
            presenter.onGroupChanged(shopOwnerGroup, jobTypeGroups)
        }
    }

    override fun showNothing() {
        presenter.onButtonChanged(nothingBtn, firstCategoryButtons)
        showCompleteButton()
        presenter.onGroupChanged(null, firstCategoryGroups)
        presenter.onGroupChanged(null, jobTypeGroups)
    }

    override fun showCompany() {
        presenter.onButtonChanged(companyBtn, jobTypeButtons)
        presenter.onGroupChanged(companyGroup, jobTypeGroups)
    }

    override fun showFounded() {
        presenter.onButtonChanged(shopOwnerBtn, jobTypeButtons)
        presenter.onGroupChanged(shopOwnerGroup, jobTypeGroups)
    }

    override fun showFreelancer() {
        presenter.onButtonChanged(freelancerBtn, jobTypeButtons)
        presenter.onGroupChanged(null, jobTypeGroups)
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    // 완료 버튼 클릭 리스너
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

}
