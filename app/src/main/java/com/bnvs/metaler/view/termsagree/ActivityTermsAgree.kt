package com.bnvs.metaler.view.termsagree

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bnvs.metaler.R
import com.bnvs.metaler.data.token.source.local.TokenLocalDataSourceImpl
import com.bnvs.metaler.data.token.source.repository.TokenRepositoryImpl
import com.bnvs.metaler.databinding.ActivityTermsAgreeBinding
import com.bnvs.metaler.network.NO_HEADER
import com.bnvs.metaler.network.RetrofitClient
import com.bnvs.metaler.network.TOKEN_EXPIRED
import com.bnvs.metaler.view.jobinput.ActivityJobInput
import com.bnvs.metaler.view.login.ActivityLogin
import com.bnvs.metaler.viewmodel.AddUserViewModel

class ActivityTermsAgree : AppCompatActivity() {

    private val TAG = "ActivityTermsAgree"

    private lateinit var binding: ActivityTermsAgreeBinding
    private lateinit var viewModel: AddUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_terms_agree
        )

        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(AddUserViewModel::class.java)

        binding.apply {
            vm = viewModel
            lifecycleOwner = this@ActivityTermsAgree
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        observeToast()
        observeDialog()
        observeErrorCode()
        observeStartJobInputActivity()
    }

    private fun observeToast() {
        viewModel.errorToastMessage.observe(
            this,
            Observer { message ->
                if (message.isNotBlank()) {
                    makeToast(message)
                }
            }
        )
    }

    private fun observeDialog() {
        viewModel.errorDialogMessage.observe(
            this,
            Observer { message ->
                if (message.isNotBlank()) {
                    makeDialog(message)
                }
            }
        )
    }

    private fun observeErrorCode() {
        viewModel.errorCode.observe(
            this,
            Observer { errorCode ->
                when (errorCode) {
                    NO_HEADER -> setAuthorizationHeader()
                    TOKEN_EXPIRED -> startLoginActivity()
                }
            }
        )
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

    private fun setAuthorizationHeader() {
        TokenRepositoryImpl(TokenLocalDataSourceImpl(this))
            .getAccessToken(
                onTokenLoaded = { token ->
                    RetrofitClient.setAccessToken(token.access_token)
                },
                onTokenNotExist = {
                    startLoginActivity()
                }
            )
    }

    private fun startLoginActivity() {
        finishAffinity()
        Intent(this, ActivityLogin::class.java).also {
            startActivity(it)
        }
    }

    private fun startJobInputActivity() {
        Intent(this, ActivityJobInput::class.java).also {
            startActivity(it)
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityTermsAgree, message, Toast.LENGTH_SHORT).show()
    }

    private fun makeDialog(message: String) {
        AlertDialog.Builder(this@ActivityTermsAgree)
            .setTitle(getString(R.string.alert))
            .setMessage(message)
            .setPositiveButton(getString(R.string.allow)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
