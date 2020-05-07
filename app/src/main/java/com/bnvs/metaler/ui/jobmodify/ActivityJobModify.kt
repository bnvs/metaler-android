package com.bnvs.metaler.ui.jobmodify

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showExpert() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNothing() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showCompany() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showFounded() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showFreelancer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyTextDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showjobModifyCompleteToast() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initClickListeners() {
        setFirstCategoryButtons()
        setJobTypeButtons()
        setCompleteButton()
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
                        else -> Log.d("ActivityJobModify", "소속 입력완료 에러")
                    }
                }
                "empty" -> {
                    presenter.completeJobInput(
                        "empty",
                        "empty"
                    )
                }
                else -> Log.d("ActivityJobModify", "소속 입력완료 에러")
            }
        }
    }
}
