package com.bnvs.metaler.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import com.bnvs.metaler.data.homeposts.model.HomePost
import com.bnvs.metaler.data.profile.model.Profile
import com.bnvs.metaler.ui.detail.ActivityDetail
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity(), ContractHome.View {

    private val TAG = "ActivityHome"

    override lateinit var presenter: ContractHome.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Create the presenter
        presenter = PresenterHome(
            this@ActivityHome,
            this@ActivityHome
        )

        // Set up Buttons
        initClickListeners()

        // 홈 탭에서 보여줄 데이터 가져오기 시작
        // 상태 바(배터리,와이파이 아이콘 표시되는 곳) 투명하게함
        presenter.run {
            start()
            setStatusBar()
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    // 사용자 프로필을 보여준다
    override fun showProfile(profile: Profile) {
        Glide.with(this@ActivityHome)
            .load(profile.profile_image_url)
            .into(profileImg)
        profileNickname.text = profile.profile_nickname
        profileEmail.text = profile.profile_email
    }

    // 재료 리사이클러뷰를 보여준다
    override fun showMaterialsList(materials: List<HomePost>) {

        val titles = listOf<TextView>(
            materialsTitle1,
            materialsTitle2,
            materialsTitle3,
            materialsTitle4,
            materialsTitle5
        )
        val tags = listOf<TextView>(
            materialsTag1,
            materialsTag2,
            materialsTag3,
            materialsTag4,
            materialsTag5
        )
        val userNames = listOf<TextView>(
            materialsUserName1,
            materialsUserName2,
            materialsUserName3,
            materialsUserName4,
            materialsUserName5
        )
        val dates = listOf<TextView>(
            materialsDate1,
            materialsDate2,
            materialsDate3,
            materialsDate4,
            materialsDate5
        )

        for (i in materials.indices) {
            val material = materials[i]
            titles[i].text = material.title
            tags[i].text = parseTagList(material.tags)
            userNames[i].text = material.profile_nickname
            dates[i].text = material.date
        }
    }

    // 가공 리사이클러뷰를 보여준다
    override fun showManufacturesList(manufactures: List<HomePost>) {
        val titles = listOf<TextView>(
            manufacturesTitle1,
            manufacturesTitle2,
            manufacturesTitle3,
            manufacturesTitle4,
            manufacturesTitle5
        )
        val tags = listOf<TextView>(
            manufacturesTag1,
            manufacturesTag2,
            manufacturesTag3,
            manufacturesTag4,
            manufacturesTag5
        )
        val userNames = listOf<TextView>(
            manufacturesUserName1,
            manufacturesUserName2,
            manufacturesUserName3,
            manufacturesUserName4,
            manufacturesUserName5
        )
        val dates = listOf<TextView>(
            manufacturesDate1,
            manufacturesDate2,
            manufacturesDate3,
            manufacturesDate4,
            manufacturesDate5
        )

        for (i in manufactures.indices) {
            val material = manufactures[i]
            titles[i].text = material.title
            tags[i].text = parseTagList(material.tags)
            userNames[i].text = material.profile_nickname
            dates[i].text = material.date
        }
    }

    private fun parseTagList(tags: List<String>): String {
        var tagString = ""
        for (tag in tags) {
            tagString += "#$tag "
        }
        return tagString
    }

    override fun showProfileNotExistToast() {
        makeToast("프로필 데이터를 불러오는데 실패했습니다")
    }

    override fun showLoadHomePostFailedToast(errorMessage: String) {
        makeToast(errorMessage)
    }

    // 게시물 상세 내용 액티비티로 이동한다
    override fun showPostDetailUi(postId: Int) {
        Intent(this@ActivityHome, ActivityDetail::class.java)
            .apply { putExtra("postId", postId) }
            .also { startActivity(it) }

        overridePendingTransition(0, 0)
    }

    private fun initClickListeners() {
        setMoreButtons()
        setTapBarButtons()
    }

    private fun setMoreButtons() {
        materialsMoreBtn.setOnClickListener { presenter.openMaterials(this, this) }
        manufactureMoreBtn.setOnClickListener { presenter.openManufactures(this, this) }
    }

    private fun setTapBarButtons() {
        // TODO : presenter 의 함수 파라미터에 view 를 넣지 않고,
        //  presenter 의 멤버변수로 선언된 view 를 사용하는 방법이 없을까?
        homeBtn.setOnClickListener { presenter.openHome(this, this) }
        materialsBtn.setOnClickListener { presenter.openMaterials(this, this) }
        manufactureBtn.setOnClickListener { presenter.openManufactures(this, this) }
        bookmarkBtn.setOnClickListener { presenter.openBookmarks(this, this) }
        myPageBtn.setOnClickListener { presenter.openMyPage(this, this) }
    }

    // 상태 바를 투명하게 하고, padding 을 조절한다
    override fun setTransparentStatusBar() {
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
        Toast.makeText(this@ActivityHome, message, Toast.LENGTH_LONG).show()
    }

}
