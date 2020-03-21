package com.example.metaler_android.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import com.example.metaler_android.materials.ActivityMaterials
import com.example.metaler_android.R
import com.example.metaler_android.detail.ActivityDetail
import com.example.metaler_android.manufactures.ActivityManufactures
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity(), ContractHome.View {

    val TAG = "ActivityHome"

    override lateinit var presenter: ContractHome.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Create the presenter
        presenter = PresenterHome(
            this@ActivityHome,
            this@ActivityHome
        )

        //상태바 투명하게 바꾸는 코드 => 대신 해당 상태바 위치에 뷰가 위치할수있음
        //상태바 뿐만 아니라 하단 소프트 버튼에도 영향끼침.. 상태바에도 뷰가 겹쳐버리는 문제발
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //현재 액티비티 레이아웃의 기준이 되는 titleBarCard에 상태바 높이 만큼 top padding 을 줌 .
        titleBarCard.setPadding(0, statusBarHeight(this), 0, 0)
        Log.d(TAG,"상태바 높이? : ${statusBarHeight(this)}")

        //소프트키 올라온 높이만큼 전체 레이아웃 하단에 padding을 줌.
        wrapConstraintLayout.setPadding(0,0,0,softMenuHeight(this))

        //탭바의 각 버튼에 맞는 액티비티로 이동하는 클릭 리스너
        materialsBtn.setOnClickListener {
            val goToMaterials = Intent(this, ActivityMaterials::class.java)
            startActivity(goToMaterials)
        }

    }

    //상태바 높이 계산
    private fun statusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId)
        else 0
    }

    //하단 소프트키 높이 구함
    private fun softMenuHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")

        var deviceHeight : Int = 0
        return if (resourceId > 0){ context.resources.getDimensionPixelSize(resourceId)
        }else 0
    }

    override fun showProfile() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMaterialsList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showManufacturesList() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMaterialsUi() {
        val intent = Intent(this@ActivityHome, ActivityMaterials::class.java)
        intent.flags.apply {
            Intent.FLAG_ACTIVITY_NO_ANIMATION
            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
    }

    override fun showManufacturesUi() {
        val intent = Intent(this@ActivityHome, ActivityManufactures::class.java)
        intent.flags.apply {
            Intent.FLAG_ACTIVITY_NO_ANIMATION
            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
    }

    override fun showPostDetailUi() {
        val intent = Intent(this@ActivityHome, ActivityDetail::class.java)
        intent.flags.apply {
            Intent.FLAG_ACTIVITY_NO_ANIMATION
            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        startActivity(intent)
    }

}
