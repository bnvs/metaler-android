package com.bnvs.metaler.ui.mypage

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.ui.jobmodify.ActivityJobModify
import com.bnvs.metaler.ui.login.ActivityLogin
import com.bnvs.metaler.ui.myposts.ActivityMyPosts
import com.bnvs.metaler.ui.termscheck.ActivityTermsCheck
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_my_page.*

class ActivityMyPage : AppCompatActivity(), ContractMyPage.View {

    private val TAG = "ActivityMyPage"

    override lateinit var presenter: ContractMyPage.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        // Create the presenter
        presenter = PresenterMyPage(
            this@ActivityMyPage,
            this@ActivityMyPage
        )

        // Set up Buttons
        initClickListeners()

        // 재료 탭 presenter 시작
        presenter.run {
            start()
        }
    }

    // 사용자 프로필을 보여준다
    override fun showProfile(profile: Profile) {
        Glide.with(this@ActivityMyPage)
            .load(profile.profile_image_url)
            .transform(CircleCrop())
            .error(R.drawable.ic_profile_x3)
            .into(profileImg)
        profileNickname.text = profile.profile_nickname
        profileEmail.text = profile.profile_email
    }

    override fun showProfileNotExistToast() {
        makeToast("프로필 데이터를 불러오는데 실패했습니다")
    }

    override fun showLocalNicknameChangeFailedToast() {
        makeToast("닉네임 수정 실패")
    }

    override fun showJobModifyUi() {
        Intent(this@ActivityMyPage, ActivityJobModify::class.java).also {
            startActivity(it)
        }
    }

    override fun showMyPostsUi() {
        Intent(this@ActivityMyPage, ActivityMyPosts::class.java).also {
            startActivity(it)
        }
    }

    override fun showTermsCheckUi() {
        Intent(this@ActivityMyPage, ActivityTermsCheck::class.java).also {
            startActivity(it)
        }
    }

    override fun showNicknameModifyDialog() {
        AlertDialog.Builder(this@ActivityMyPage)
            .setTitle("별명 변경")
            .setMessage("변경할 별명을 입력해주세요.")
            .setView(R.layout.dialog_nickname_input)
            .setPositiveButton("확인") { dialog, which ->
                val f = dialog as Dialog
                val input: EditText = f.findViewById(R.id.nicknameInputEditTxt)
                val nickname = input.text.toString()
                presenter.modifyNickName(nickname)
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    override fun showLogoutDialog() {
        AlertDialog.Builder(this@ActivityMyPage)
            .setTitle("로그아웃")
            .setMessage("로그아웃 하시겠습니까?")
            .setPositiveButton("로그아웃") { dialog, which ->
                presenter.logout()
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    override fun showWithdrawalDialog() {
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
                presenter.withdrawal(confirmInput)
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    override fun showInvalidWithdrawalConfirmInputToast() {
        makeToast("\"동의합니다\"를 올바르게 입력해주세요")
    }

    override fun openLoginActivity() {
        finishAffinity()
        Intent(this, ActivityLogin::class.java).also {
            startActivity(it)
        }
    }

    private fun initClickListeners() {
        setMenuButtons()
        setTapBarButtons()
    }

    //메뉴의 텍스트, more 버튼 둘 다 누를 수 있도록함
    private fun setMenuButtons() {
        nicknameBtn.setOnClickListener { presenter.openNicknameModify() }
        nicknameMoreBtn.setOnClickListener { presenter.openNicknameModify() }

        jobBtn.setOnClickListener { presenter.openJobModify() }
        jobMoreBtn.setOnClickListener { presenter.openJobModify() }

        myPostsBtn.setOnClickListener { presenter.openMyPosts() }
        myPostsMoreBtn.setOnClickListener { presenter.openMyPosts() }

        termsBtn.setOnClickListener { presenter.openTerms() }
        termsMoreBtn.setOnClickListener { presenter.openTerms() }

        logoutBtn.setOnClickListener { presenter.openLogout() }
        logoutMoreBtn.setOnClickListener { presenter.openLogout() }

        withdrawalBtn.setOnClickListener { presenter.openWithdrawal() }
        withdrawalMoreBtn.setOnClickListener { presenter.openWithdrawal() }

    }

    private fun setTapBarButtons() {
        homeBtn.setOnClickListener { presenter.openHome(this, this) }
        materialsBtn.setOnClickListener { presenter.openMaterials(this, this) }
        manufactureBtn.setOnClickListener { presenter.openManufactures(this, this) }
        bookmarkBtn.setOnClickListener { presenter.openBookmarks(this, this) }
        myPageBtn.setOnClickListener { presenter.openMyPage(this, this) }
    }

    override fun showLogoutSuccessToast() {
        makeToast("로그아웃 되었습니다")
    }

    override fun showWithdrawalSuccessToast() {
        makeToast("회원탈퇴가 완료되었습니다.\n그동안 메탈러 서비스를 이용해 주셔서 감사합니다.")
    }

    override fun showErrorToast(errorMessage: String) {
        makeToast(errorMessage)
    }

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityMyPage, message, Toast.LENGTH_LONG).show()
    }

}
