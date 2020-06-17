package com.bnvs.metaler.view.jobinput

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.ActivityJobInputBinding
import com.bnvs.metaler.util.base.BaseActivity
import com.bnvs.metaler.view.home.ActivityHome
import org.koin.android.ext.android.inject

class ActivityJobInput : BaseActivity<ViewModelJobInput>() {

    private val TAG = "ActivityJobInput"

    override val viewModel: ViewModelJobInput by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityJobInputBinding>(
            this,
            R.layout.activity_job_input
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityJobInput
        }

        observeViewModel()

    }

    override fun observeViewModel() {
        super.observeViewModel()
        observeStartHomeActivity()
        observeBackToTermsAgreeActivity()
    }

    private fun observeBackToTermsAgreeActivity() {
        viewModel.backToTermsAgreeActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    finish()
                }
            }
        )
    }

    private fun observeStartHomeActivity() {
        viewModel.openHomeActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    startHomeActivity()
                }
            }
        )
    }

    private fun startHomeActivity() {
        finishAffinity()
        Intent(this, ActivityHome::class.java).also {
            startActivity(it)
        }
    }

}
