package com.example.metaler_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity() {

    val TAG = "ActivityHome"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //상태바 투명하게 바꾸는 코드
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        //상태바 높이만큼 상단에 패딩값을 줌
        titleBarCard.setPadding(0,getStatusBarHeight()/3,0,0) //=>아예 화면에서 사라짐 => 상태바 높이 로그 찍어보면 100이 넘는 수치로 나오는데, 이는 pixel값임. pixel을 dp로 바꿔야함

//        materialsCard.setPadding(0,getStatusBarHeight(),0,0) //=>얘는 패딩이 잘 먹히는데 titleBarCard는 왜 안될까..
//        currentTitleText.setPadding(0,getStatusBarHeight(),0,0)

    }

    //상태바 높이 계산
    private fun getStatusBarHeight() : Int {
        var result: Int = 0
        var resourceId : Int = getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId)

        Log.d(TAG,"상태바 높이 ? :${result}")
        return result
    }
}
