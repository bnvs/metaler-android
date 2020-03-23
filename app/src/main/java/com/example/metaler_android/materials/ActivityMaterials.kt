package com.example.metaler_android.materials

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.metaler_android.detail.ActivityDetail
import com.example.metaler_android.home.ActivityHome
import com.example.metaler_android.R
import com.example.metaler_android.manufactures.ActivityManufactures
import kotlinx.android.synthetic.main.activity_materials.*

class ActivityMaterials : AppCompatActivity(), ContractMaterials.View {

    private val TAG = "ActivityMaterials"

    override lateinit var presenter: ContractMaterials.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materials)

        presenter = PresenterMaterials(
            this@ActivityMaterials,
            this@ActivityMaterials
        )

        // Set up Buttons
        initClickListeners()

    }

    override fun showCategories() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPosts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPostDetailUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchTags() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearSearchTagBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * TapBarContract.View 에서 상속받은 함수
     * showHomeUi() ~ showMyPageUi() 까지
     * */
    override fun showHomeUi() {
        Intent(this@ActivityMaterials, ActivityHome::class.java).also {
            startActivity(it)
        }
    }

    override fun showMaterialsUi() {
        Intent(this@ActivityMaterials, ActivityMaterials::class.java).also {
            startActivity(it)
        }
    }

    override fun showManufacturesUi() {
        Intent(this@ActivityMaterials, ActivityManufactures::class.java).also {
            startActivity(it)
        }
    }

    override fun showBookmarksUi() {
        /*Intent(this@ActivityMaterials, ActivityBookmarks::class.java).also {
            startActivity(it)
        }*/
    }

    override fun showMyPageUi() {
        /*Intent(this@ActivityMaterials, ActivityMyPage::class.java).also {
            startActivity(it)
        }*/
    }

    private fun initClickListeners() {
        setTitleBarButtons()
        setTapBarButtons()
    }

    private fun setTitleBarButtons() {
        // 글작성, 글검색 버튼 클릭 리스너 달아주기
    }

    private fun setTapBarButtons() {
        homeBtn.setOnClickListener { presenter.openHome() }
        materialsBtn.setOnClickListener { presenter.openMaterials() }
        manufactureBtn.setOnClickListener { presenter.openManufactures() }
        bookmarkBtn.setOnClickListener { presenter.openBookmarks() }
        myPageBtn.setOnClickListener { presenter.openMyPage() }
    }

    //눌린 카테고리 버튼의 뷰 속성을 활성화 상태로 변경하는 메소드
    private fun activeCategoryBtn(categoryBtn : TextView){
        categoryBtn.background = ContextCompat.getDrawable(this,
            R.drawable.active_bar
        )
        categoryBtn.setTextColor(ContextCompat.getColor(this,
            R.color.colorPurple
        ))
    }
}
