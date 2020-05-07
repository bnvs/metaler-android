package com.bnvs.metaler.ui.jobmodify

import android.widget.EditText
import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractJobModify {
    interface View : BaseView<Presenter> {
        fun showStudent()

        fun showExpert()

        fun showNothing()

        fun showCompany()

        fun showFounded()

        fun showFreelancer()

        fun showEmptyTextDialog()

        fun showjobModifyCompleteToast()
    }

    interface Presenter : BasePresenter {
        fun getUserJob()

        fun openStudent()

        fun openExpert()

        fun openNothing()

        fun openCompany()

        fun openFounded()

        fun openFreelancer()

        fun getSelectedJob(): String

        fun getSelectedJobType(): String

        fun getLastSelectedJobType(): String

        fun completeJobInput(jobTypeInput: String, jobDetailInput: String)

        fun modifyUserJob()

        fun getString(editText: EditText): String
    }
}