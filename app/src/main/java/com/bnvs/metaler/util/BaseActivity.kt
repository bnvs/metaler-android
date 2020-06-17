package com.bnvs.metaler.util

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bnvs.metaler.R
import com.bnvs.metaler.data.token.source.local.TokenLocalDataSourceImpl
import com.bnvs.metaler.data.token.source.repository.TokenRepositoryImpl
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.constants.NO_HEADER
import com.bnvs.metaler.util.constants.TOKEN_EXPIRED
import com.bnvs.metaler.view.bookmarks.ActivityBookmarks
import com.bnvs.metaler.view.home.ActivityHome
import com.bnvs.metaler.view.login.ActivityLogin
import com.bnvs.metaler.view.manufactures.ActivityManufactures
import com.bnvs.metaler.view.materials.ActivityMaterials
import com.bnvs.metaler.view.mypage.ActivityMyPage

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModel: VM

    protected open fun observeViewModel() {
        observeToast()
        observeDialog()
        observeErrorCode()
        observeStartHomeActivity()
        observeStartMaterialsActivity()
        observeStartManufacturesActivity()
        observeStartBookmarksActivity()
        observeStartMyPageActivity()
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

    private fun observeStartMaterialsActivity() {
        viewModel.openMaterialsActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    startMaterialsActivity()
                }
            }
        )
    }

    private fun observeStartManufacturesActivity() {
        viewModel.openManufacturesActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    startManufacturesActivity()
                }
            }
        )
    }

    private fun observeStartBookmarksActivity() {
        viewModel.openBookmarksActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    startBookmarksActivity()
                }
            }
        )
    }

    private fun observeStartMyPageActivity() {
        viewModel.openMyPageActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    startMyPageActivity()
                }
            }
        )
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

    private fun setAuthorizationHeader() {
        TokenRepositoryImpl(TokenLocalDataSourceImpl(this))
            .getAccessToken(
                onTokenLoaded = { token ->
                    NetworkUtil.setAccessToken(token.access_token)
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

    private fun startHomeActivity() {
        Intent(this, ActivityHome::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        }.also {
            startActivity(it)
            finishActivity()
        }
    }

    private fun startMaterialsActivity() {
        Intent(this, ActivityMaterials::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        }.also {
            startActivity(it)
            finishActivity()
        }
    }

    private fun startManufacturesActivity() {
        Intent(this, ActivityManufactures::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        }.also {
            startActivity(it)
            finishActivity()
        }
    }

    private fun startBookmarksActivity() {
        Intent(this, ActivityBookmarks::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        }.also {
            startActivity(it)
            finishActivity()
        }
    }

    private fun startMyPageActivity() {
        Intent(this, ActivityMyPage::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        }.also {
            startActivity(it)
            finishActivity()
        }
    }

    private fun finishActivity() {
        finish()
        overridePendingTransition(0, 0)
    }

    protected fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun makeDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.alert))
            .setMessage(message)
            .setPositiveButton(getString(R.string.allow)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}