package com.bnvs.metaler.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bnvs.metaler.R
import com.bnvs.metaler.data.token.source.local.TokenLocalDataSourceImpl
import com.bnvs.metaler.data.token.source.repository.TokenRepositoryImpl
import com.bnvs.metaler.databinding.ActivityHomeBinding
import com.bnvs.metaler.network.NetworkUtil
import com.bnvs.metaler.util.constants.NO_HEADER
import com.bnvs.metaler.util.constants.TOKEN_EXPIRED
import com.bnvs.metaler.view.bookmarks.ActivityBookmarks
import com.bnvs.metaler.view.detail.ActivityDetail
import com.bnvs.metaler.view.login.ActivityLogin
import com.bnvs.metaler.view.manufactures.ActivityManufactures
import com.bnvs.metaler.view.materials.ActivityMaterials
import com.bnvs.metaler.view.mypage.ActivityMyPage
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class ActivityHome : AppCompatActivity() {

    private val TAG = "ActivityHome"

    private val viewModel: ViewModelHome by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityHome
        }

        setTransparentStatusBar()
        observeViewModel()

    }

    private fun observeViewModel() {
        observeToast()
        observeDialog()
        observeErrorCode()
        observeStartMaterialsActivity()
        observeStartManufacturesActivity()
        observeStartBookmarksActivity()
        observeStartMyPageActivity()
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

    private fun observeStartDetailActivity() {
        viewModel.openDetailActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    val postId = viewModel.postId.value
                    if (postId == null) {
                        makeToast("상세게시물을 볼 수 없습니다")
                    } else {
                        startDetailActivity(postId)
                    }
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

    // 게시물 상세 내용 액티비티로 이동한다
    private fun startDetailActivity(postId: Int) {
        Intent(this@ActivityHome, ActivityDetail::class.java)
            .apply { putExtra("postId", postId) }
            .also { startActivity(it) }

        overridePendingTransition(0, 0)
    }

    private fun startMaterialsActivity() {
        Intent(this, ActivityMaterials::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        }.also { startActivity(it) }
    }

    private fun startManufacturesActivity() {
        Intent(this, ActivityManufactures::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        }.also { startActivity(it) }
    }

    private fun startBookmarksActivity() {
        Intent(this, ActivityBookmarks::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        }.also { startActivity(it) }
    }

    private fun startMyPageActivity() {
        Intent(this, ActivityMyPage::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
        }.also { startActivity(it) }
    }

    private fun finishActivity() {
        finish()
        overridePendingTransition(0, 0)
    }

    // 상태 바를 투명하게 하고, padding 을 조절한다
    private fun setTransparentStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //현재 액티비티 레이아웃의 기준이 되는 titleBarCard에 상태바 높이 만큼 top padding 을 줌 .
        titleBarCard.setPadding(0, statusBarHeight(this), 0, 0)
        Log.d(TAG, "상태바 높이? : ${statusBarHeight(this)}")

        //소프트키 올라온 높이만큼 전체 레이아웃 하단에 padding을 줌.
        wrapConstraintLayout.setPadding(0, 0, 0, softMenuHeight(this))
    }

    //상태바 높이 계산
    private fun statusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId)
        else 0
    }

    //하단 소프트키 높이 구함
    private fun softMenuHeight(context: Context): Int {
        val resourceId =
            context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityHome, message, Toast.LENGTH_SHORT).show()
    }

    private fun makeDialog(message: String) {
        AlertDialog.Builder(this@ActivityHome)
            .setTitle(getString(R.string.alert))
            .setMessage(message)
            .setPositiveButton(getString(R.string.allow)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

}
