package com.bnvs.metaler.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.ui.jobmodify.ActivityJobModify
import com.bnvs.metaler.ui.myposts.ActivityMyPosts
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSuccessDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNicknameModifyDialog() {

    }

    override fun showLogoutDialog() {

    }

    override fun showWithdrawalDialog() {

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

    private fun makeToast(message: String) {
        Toast.makeText(this@ActivityMyPage, message, Toast.LENGTH_LONG).show()
    }

}
