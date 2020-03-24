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
}
