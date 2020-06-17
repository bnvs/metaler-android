package com.bnvs.metaler.view.termsagree

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.ActivityTermsAgreeBinding
import com.bnvs.metaler.util.base.BaseActivity
import com.bnvs.metaler.view.jobinput.ActivityJobInput
import org.koin.android.ext.android.inject

class ActivityTermsAgree : BaseActivity<ViewModelTermsAgree>() {

    override val viewModel: ViewModelTermsAgree by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityTermsAgreeBinding>(
            this,
            R.layout.activity_terms_agree
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityTermsAgree
        }

        observeViewModel()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        observeStartJobInputActivity()
    }

    private fun observeStartJobInputActivity() {
        viewModel.openJobInputActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    startJobInputActivity()
                }
            }
        )
    }

    private fun startJobInputActivity() {
        Intent(this, ActivityJobInput::class.java).also {
            startActivity(it)
        }
    }
}
