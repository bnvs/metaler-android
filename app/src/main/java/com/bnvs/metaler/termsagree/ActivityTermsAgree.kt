package com.bnvs.metaler.termsagree

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import com.bnvs.metaler.R
import com.bnvs.metaler.data.user.AddUserRequest
import kotlinx.android.synthetic.main.activity_terms_agree.*
import org.json.JSONObject

class ActivityTermsAgree : AppCompatActivity(), ContractTermsAgree.View {

    private val TAG = "ActivityTermsAgree"

    override lateinit var presenter: ContractTermsAgree.Presenter

    lateinit var addUserRequest: AddUserRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_agree)

        // Create the presenter
        presenter = PresenterTermsAgree(
            this@ActivityTermsAgree
        )

        // Set up Buttons
        initClickListeners()

    }

    override fun showTermsWebView() {

    }

    override fun showEssentialAgreeNotCheckedDialog() {

    }

    override fun showJobInputUi(result: JSONObject) {

    }

    private fun initClickListeners() {
        setCheckBoxes()
        setNextButton()
    }

    private fun setNextButton() {

    }

    private fun setCheckBoxes() {
        allCheckBtn.setOnClickListener { onCheckChanged(allCheckBtn) }
        firstCheckBtn.setOnClickListener { onCheckChanged(firstCheckBtn) }
        secondCheckBtn.setOnClickListener { onCheckChanged(secondCheckBtn) }
        thirdCheckBtn.setOnClickListener { onCheckChanged(thirdCheckBtn) }
        fourthCheckBtn.setOnClickListener { onCheckChanged(fourthCheckBtn) }
    }

    private fun onCheckChanged(compoundButton: CompoundButton) {
        when(compoundButton.id) {
            R.id.allCheckBtn -> {
                if (allCheckBtn.isChecked) {
                    firstCheckBtn.isChecked = true
                    secondCheckBtn.isChecked = true
                    thirdCheckBtn.isChecked = true
                    fourthCheckBtn.isChecked = true
                }else {
                    firstCheckBtn.isChecked = false
                    secondCheckBtn.isChecked = false
                    thirdCheckBtn.isChecked = false
                    fourthCheckBtn.isChecked = false
                }
            }
            else -> {
                allCheckBtn.isChecked = (
                        firstCheckBtn.isChecked
                        && secondCheckBtn.isChecked
                        && thirdCheckBtn.isChecked
                        && fourthCheckBtn.isChecked)
            }
        }
    }

}
