package com.bnvs.metaler.view.jobmodify

import android.widget.EditText
import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractJobModify {
    interface View : BaseView<Presenter> {
        fun showStudent()

        fun showExpert(jobType: String)

        fun showNothing()

        fun showCompany()

        fun showFounded()

        fun showFreelancer()

        fun setStudent(jobType: String, jobDetail: String)

        fun setCompany(jobDetail: String)

        fun setFounded(jobDetail: String)

        fun showEmptyTextDialog()

        fun hideSoftInput()

        fun showErrorMessage(errorMessage: String)

        fun showModifyTheJobDialog()

        fun showJobModifyCompleteDialog()
    }

    interface Presenter : BasePresenter {
        fun getUserJob()

        fun setUserJob()

        fun openStudent()

        fun openExpert()

        fun openNothing()

        fun openCompany()

        fun openFounded()

        fun openFreelancer()

        fun getSelectedJob(): String

        fun getSelectedJobType(): String

        fun completeJobInput(jobTypeInput: String, jobDetailInput: String)

        fun modifyUserJob()

        fun getString(editText: EditText): String
    }
}