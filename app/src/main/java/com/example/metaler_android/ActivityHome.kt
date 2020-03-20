package com.example.metaler_android

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import com.example.metaler_android.Materials.ActivityMaterials
import kotlinx.android.synthetic.main.activity_home.*

class ActivityHome : AppCompatActivity() {

    val TAG = "ActivityHome"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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

        //        hasSoftMenu()



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

//    //소프트키 버튼이 유무를 알아내는 함수 -> 사용안함
//    private fun hasSoftMenu(): Boolean {
//        //메뉴버튼 유무
//        var hasMenuKey : Boolean = ViewConfiguration.get(getApplicationContext()).hasPermanentMenuKey()
//
//        //뒤로가기 버튼 유무
//        var hasBackKey : Boolean = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
//
////        Log.d(TAG,"hasMenuKey? :${hasMenuKey} // hasBackKey? :${hasBackKey}")
//
//
//        if(!hasMenuKey && !hasBackKey) {
//            Log.d(TAG,"no hasMenuKey")
//            return false
//        } else {
//            Log.d(TAG,"hasMenuKey ${hasMenuKey}   ${hasBackKey}")
//            return true
//        }
//    }

}
