package com.example.metaler_android.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.metaler_android.R
import com.example.metaler_android.bookmark.ActivityBookmark
import com.example.metaler_android.data.profile.Profile
import com.example.metaler_android.home.ActivityHome
import com.example.metaler_android.manufactures.ActivityManufactures
import com.example.metaler_android.materials.ActivityMaterials
import com.example.metaler_android.jobmodify.ActivityJobModify
import com.example.metaler_android.myposts.ActivityMyPosts

import kotlinx.android.synthetic.main.activity_my_page.*

class ActivityMyPage : AppCompatActivity(), ContractMyPage.View {

    private val TAG = "ActivityMyPage"

    override lateinit var presenter: ContractMyPage.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
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

    /**
     * TapBarContract.View 에서 상속받은 함수
     * showHomeUi() ~ showMyPageUi() 까지
     * */
    override fun showHomeUi() {
        Intent(this@ActivityMyPage, ActivityHome::class.java).also {
            startActivity(it)
        }
    }

    override fun showMaterialsUi() {
        Intent(this@ActivityMyPage, ActivityMaterials::class.java).also {
            startActivity(it)
        }
    }

    override fun showManufacturesUi() {
        Intent(this@ActivityMyPage, ActivityManufactures::class.java).also {
            startActivity(it)
        }
    }

    override fun showBookmarksUi() {
        Intent(this@ActivityMyPage, ActivityBookmark::class.java).also {
            startActivity(it)
        }
    }

    override fun showMyPageUi() {
        Intent(this@ActivityMyPage, ActivityMyPage::class.java).also {
            startActivity(it)
        }
    }

    override fun showNicknameModifyDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLogoutDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showWithdrawalDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initClickListeners() {
        setMenuButtons()
        setTapBarButtons()
    }

    //메뉴의 텍스트, more 버튼 둘 다 누를 수 있도록함
    private fun setMenuButtons() {
        jobBtn.setOnClickListener { presenter.openJobModify() }
        jobMoreBtn.setOnClickListener { presenter.openJobModify() }

        myPostsBtn.setOnClickListener { presenter.openMyPosts() }
        myPostsMoreBtn.setOnClickListener { presenter.openMyPosts() }
    }

    private fun setTapBarButtons() {
        homeBtn.setOnClickListener { presenter.openHome() }
        materialsBtn.setOnClickListener { presenter.openMaterials() }
        manufactureBtn.setOnClickListener { presenter.openManufactures() }
        bookmarkBtn.setOnClickListener { presenter.openBookmarks() }
        myPageBtn.setOnClickListener { presenter.openMyPage() }
    }

}
