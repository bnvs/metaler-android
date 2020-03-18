package com.example.metaler_android

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.homeBtn
import kotlinx.android.synthetic.main.activity_materials.*

class ActivityMaterials : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materials)

        //탭바의 각 버튼에 맞는 액티비티로 이동하는 클릭 리스너
        homeBtn.setOnClickListener {
            val goToHome = Intent(this, ActivityHome::class.java)
            startActivity(goToHome)
        }

        //카테고리 버튼 색상 초기화
        // 활성화된 버튼만 글자 색상 변경하고, 비활성화된 버튼은 백그라운드소스 없애기
        allBtn.setTextColor(ContextCompat.getColor(this,R.color.colorLightGrey))
        cooperBtn.setBackgroundResource(0)
        stainlessBtn.setBackgroundResource(0)
        aluminiumBtn.setBackgroundResource(0)
        nickelBtn.setBackgroundResource(0)
        steelBtn.setBackgroundResource(0)
        toolsBtn.setBackgroundResource(0)
        chemicalBtn.setBackgroundResource(0)
        othersBtn.setBackgroundResource(0)

    }

    override fun onResume() {
        super.onResume()



        cooperBtn.setOnClickListener {
            allBtn.setBackgroundResource(0)
            allBtn.setTextColor(ContextCompat.getColor(this,R.color.colorLightGrey))
            cooperBtn.setTextColor(ContextCompat.getColor(this,R.color.colorPurple))
            cooperBtn.background = ContextCompat.getDrawable(this,R.drawable.active_bar)
            stainlessBtn.setBackgroundResource(0)
            aluminiumBtn.setBackgroundResource(0)
            nickelBtn.setBackgroundResource(0)
            steelBtn.setBackgroundResource(0)
            toolsBtn.setBackgroundResource(0)
            chemicalBtn.setBackgroundResource(0)
            othersBtn.setBackgroundResource(0)
        }

        stainlessBtn.setOnClickListener {
            allBtn.setBackgroundResource(0)
            allBtn.setTextColor(ContextCompat.getColor(this,R.color.colorLightGrey))
            cooperBtn.setBackgroundResource(0)
            cooperBtn.setTextColor(ContextCompat.getColor(this,R.color.colorLightGrey))
            stainlessBtn.setTextColor(ContextCompat.getColor(this,R.color.colorPurple))
            stainlessBtn.background = ContextCompat.getDrawable(this,R.drawable.active_bar)
            aluminiumBtn.setBackgroundResource(0)
            nickelBtn.setBackgroundResource(0)
            steelBtn.setBackgroundResource(0)
            toolsBtn.setBackgroundResource(0)
            chemicalBtn.setBackgroundResource(0)
            othersBtn.setBackgroundResource(0)

        }
    }
}
