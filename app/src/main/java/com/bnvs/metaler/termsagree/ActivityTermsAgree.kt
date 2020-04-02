package com.bnvs.metaler.termsagree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AlertDialog
import com.bnvs.metaler.R
import com.bnvs.metaler.data.user.AddUserRequest
import com.bnvs.metaler.jobinput.ActivityJobInput
import kotlinx.android.synthetic.main.activity_terms_agree.*
import org.json.JSONObject

class ActivityTermsAgree : AppCompatActivity(), ContractTermsAgree.View {

    private val TAG = "ActivityTermsAgree"

    override lateinit var presenter: ContractTermsAgree.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_agree)

        // Create the presenter
        presenter = PresenterTermsAgree(
            this@ActivityTermsAgree
        )

        // Set up Buttons
        initClickListeners()

        presenter.apply {
            getAddUserRequest(intent)
        }
    }

    override fun showTermsWebView() {
        // TODO : 약관 내용 보여주는 코드 추가
    }

    override fun showEssentialAgreeNotCheckedDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.terms_alert))
                .setMessage(getString(R.string.essential_terms_guide))
                .show()
        }
    }

    override fun showJobInputUi(addUserRequest: AddUserRequest) {
        val intent = Intent(this, ActivityJobInput::class.java)
        intent.putExtra("addUserRequest", addUserRequest)
        startActivity(intent)
    }

    private fun initClickListeners() {
        setCheckBoxes()
        setNextButton()
    }

    private fun setNextButton() {
        nextBtn.setOnClickListener { presenter.openJobInput(checkAgrees()) }
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

    private fun checkAgrees(): JSONObject {
        val result = JSONObject()
        return if(firstCheckBtn.isChecked && secondCheckBtn.isChecked) {
            result.run {
                put("valid", true)
                put("firstTerm", firstCheckBtn.isChecked)
                put("secondTerm", secondCheckBtn.isChecked)
                put("thirdTerm", thirdCheckBtn.isChecked)
                put("fourthTerm", fourthCheckBtn.isChecked)
            }
        } else {
            result.put("valid", false)
        }
    }
}
