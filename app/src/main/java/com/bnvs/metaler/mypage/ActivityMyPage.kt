package com.bnvs.metaler.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bnvs.metaler.R
import com.bnvs.metaler.data.profile.Profile
import com.bnvs.metaler.jobmodify.ActivityJobModify
import com.bnvs.metaler.myposts.ActivityMyPosts

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
            .into(profileImg)
        profileNickname.text = profile.profile_nickname
        profileEmail.text = profile.profile_email
    }

    override fun showJobModifyUi() {
        Intent(this@ActivityMyPage, ActivityJobModify::class.java).also {
            startActivity(it)
        }
    }

    override fun showMyPostsUi() {
        Intent(this@ActivityMyPage, ActivityMyPosts::class.java).also {
            startActivity(it)
        }       }

    override fun showNicknameModifyDialog() {
        val editText = EditText(this)
        val builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.nickname_modify_title))
        builder.setMessage(getString(R.string.nickname_modify_content))
        builder.setView(editText)

        builder.setPositiveButton(getString(R.string.allow)){ dialogInterface, i ->
                //TODO : 별명 입력 후 확인 눌렀을 때 기능 추가하기
            }
            .setNegativeButton(getString(R.string.cancel)){ dialogInterface, i ->

            }
            .show()
    }

    override fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.logout_title))
        builder.setMessage(getString(R.string.logout_content))

        builder.setPositiveButton(getString(R.string.logout_allow)){ dialogInterface, i ->
            //TODO : 로그아웃 눌렀을 때 기능 추가하기
        }
            .setNegativeButton(getString(R.string.cancel)){ dialogInterface, i ->

            }
            .show()
    }

    override fun showWithdrawalDialog() {
        val editText = EditText(this)
        val builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.withdrawal_title))
        builder.setMessage(getString(R.string.withdrawal_content))

        builder.setPositiveButton(getString(R.string.withdrawal_allow)) { dialogInterface, i ->
            builder.setTitle(getString(R.string.withdrawal_recheck_title))
            builder.setMessage(getString(R.string.withdrawal_recheck_content))
            builder.setView(editText)

            builder.setPositiveButton(getString(R.string.withdrawal_allow)){ dialogInterface, i ->
                //TODO : 회원탈퇴 재확인 이메일 입력 받고 탈퇴 눌렀을 때 기능 추가하기
            }
                .setNegativeButton(getString(R.string.cancel)){ dialogInterface, i ->

                }
                .show()
        }
            .setNegativeButton(getString(R.string.cancel)){ dialogInterface, i ->

            }
            .show()
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

}
