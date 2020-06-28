package com.bnvs.metaler.view.mypage

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.ActivityMyPageBinding
import com.bnvs.metaler.util.base.tap.BaseTapActivity
import com.bnvs.metaler.view.jobmodify.ActivityJobModify
import com.bnvs.metaler.view.login.ActivityLogin
import com.bnvs.metaler.view.myposts.ActivityMyPosts
import com.bnvs.metaler.view.termscheck.ActivityTermsCheck
import org.koin.android.ext.android.inject

class ActivityMyPage : BaseTapActivity<ViewModelMyPage>() {

    private val TAG = "ActivityMyPage"

    override val viewModel: ViewModelMyPage by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMyPageBinding>(
            this,
            R.layout.activity_my_page
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityMyPage
        }

        observeViewModel()
    }


    override fun observeViewModel() {
        super.observeViewModel()
        observeOpenNicknameModifyDialog()
        observeOpenJobModifyActivity()
        observeOpenMyPostsActivity()
        observeOpenTermsCheckActivity()
        observeOpenLogoutDialog()
        observeOpenWithdrawalDialog()
        observeOpenLoginActivity()
    }

    private fun observeOpenNicknameModifyDialog() {
        viewModel.openNicknameModifyDialog.observe(
            this,
            Observer { openDialog ->
                if (openDialog) {
                    openNicknameModifyDialog()
                }
            }
        )
    }

    private fun observeOpenJobModifyActivity() {
        viewModel.openJobModifyActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    openJobModifyActivity()
                }
            }
        )
    }

    private fun observeOpenMyPostsActivity() {
        viewModel.openMyPostsActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    openMyPostsActivity()
                }
            }
        )
    }

    private fun observeOpenTermsCheckActivity() {
        viewModel.openTermsCheckActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    openTermsCheckActivity()
                }
            }
        )
    }

    private fun observeOpenLogoutDialog() {
        viewModel.openLogoutDialog.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    openLogoutDialog()
                }
            }
        )
    }

    private fun observeOpenWithdrawalDialog() {
        viewModel.openWithdrawalDialog.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    openWithdrawalDialog()
                }
            }
        )
    }

    private fun observeOpenLoginActivity() {
        viewModel.openLoginActivity.observe(
            this,
            Observer { startActivity ->
                if (startActivity) {
                    openLoginActivity()
                }
            }
        )
    }

    private fun openJobModifyActivity() {
        Intent(this, ActivityJobModify::class.java).also {
            startActivity(it)
        }
    }

    private fun openMyPostsActivity() {
        Intent(this, ActivityMyPosts::class.java).also {
            startActivity(it)
        }
    }

    private fun openTermsCheckActivity() {
        Intent(this, ActivityTermsCheck::class.java).also {
            startActivity(it)
        }
    }

    private fun openNicknameModifyDialog() {
        AlertDialog.Builder(this@ActivityMyPage)
            .setTitle("별명 변경")
            .setMessage("변경할 별명을 입력해주세요.")
            .setView(R.layout.dialog_nickname_input)
            .setPositiveButton("확인") { dialog, which ->
                val f = dialog as Dialog
                val input: EditText = f.findViewById(R.id.nicknameInputEditTxt)
                val nickname = input.text.toString()
                viewModel.modifyNickname(nickname)
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    private fun openLogoutDialog() {
        AlertDialog.Builder(this@ActivityMyPage)
            .setTitle("로그아웃")
            .setMessage("로그아웃 하시겠습니까?")
            .setPositiveButton("로그아웃") { dialog, which ->
                viewModel.logout()
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    private fun openWithdrawalDialog() {
        AlertDialog.Builder(this@ActivityMyPage)
            .setTitle("회원 탈퇴")
            .setMessage(
                "계정을 삭제하시겠습니까?\n" +
                        "계정 삭제시 지금까지 작성하신 게시물은 자동으로 삭제되지 않습니다.\n" +
                        "상기 내용에 동의하시는 경우, \"동의합니다\"를 입력 후 탈퇴를 누르면 계정이 삭제됩니다"
            )
            .setView(R.layout.dialog_withdrawal_confirm_input)
            .setPositiveButton("탈퇴") { dialog, which ->
                val f = dialog as Dialog
                val input: EditText = f.findViewById(R.id.withdrawalConfirmInputEditTxt)
                val confirmInput = input.text.toString()
                viewModel.withdrawal(confirmInput)
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }


    private fun openLoginActivity() {
        finishAffinity()
        Intent(this, ActivityLogin::class.java).also {
            startActivity(it)
        }
    }

}
