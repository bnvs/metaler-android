package com.bnvs.metaler.jobinput

import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import com.bnvs.metaler.BasePresenter
import com.bnvs.metaler.BaseView

interface ContractJobInput {
    interface View: BaseView<Presenter> {
        fun showStudent()

        fun showExptert()

        fun showNothing()

        fun showCompany()

        fun showFounded()

        fun showFreelancer()

        fun showCompleteButton()

        fun showEmptyTextDialog()

        fun showJoinCompleteDialog()

        fun showHomeUi()
    }

    interface Presenter: BasePresenter {
        fun getAddUserRequest()

        fun openStudent()

        fun openExpert()

        fun openNothing()

        fun openCompany()

        fun openFounded()

        fun openFreelancer()

        fun getSelectedJob(): String

        fun getSelectedJobType(): String

        fun getlastSelectedExpertJobType(): String

        fun setButtonEnabled(button: TextView, b: Boolean)

        fun onButtonChanged(clickedButton: TextView, buttons: List<TextView>)

        fun onGroupChanged(groupToShow: Group?, groups: List<Group>)

        fun completeJobInput(jobTypeInput: String?, jobDetailInput: String?)

        fun getString(editText: EditText): String
    }
}