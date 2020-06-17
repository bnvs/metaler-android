package com.bnvs.metaler.util.base.tap

import android.content.Intent
import androidx.lifecycle.Observer
import com.bnvs.metaler.util.base.BaseActivity
import com.bnvs.metaler.view.bookmarks.ActivityBookmarks
import com.bnvs.metaler.view.home.ActivityHome
import com.bnvs.metaler.view.manufactures.ActivityManufactures
import com.bnvs.metaler.view.materials.ActivityMaterials
import com.bnvs.metaler.view.mypage.ActivityMyPage

abstract class BaseTapActivity<VM : BaseTapViewModel> : BaseActivity<VM>() {

    override fun observeViewModel() {
        super.observeViewModel()
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

}