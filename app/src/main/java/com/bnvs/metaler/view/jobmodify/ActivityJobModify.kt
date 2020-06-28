package com.bnvs.metaler.view.jobmodify

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.ActivityJobModifyBinding
import com.bnvs.metaler.util.base.BaseActivity
import kotlinx.android.synthetic.main.activity_job_modify.*
import org.koin.android.ext.android.inject

class ActivityJobModify : BaseActivity<ViewModelJobModify>() {

    override val viewModel: ViewModelJobModify by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityJobModifyBinding>(
            this,
            R.layout.activity_job_modify
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityJobModify
        }

        observeViewModel()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        observeFinishJobModifyActivity()
    }

    private fun observeFinishJobModifyActivity() {
        viewModel.finishThisActivity.observe(
            this,
            Observer { finishActivity ->
                if (finishActivity) {
                    hideSoftInput()
                    finish()
                }
            }
        )
    }

    private fun hideSoftInput() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(universityNameInput.windowToken, 0)
    }
}
