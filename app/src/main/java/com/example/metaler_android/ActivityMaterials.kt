package com.example.metaler_android

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        allBtn.setTextColor(resources.getColor(R.color.colorPurple))
        cooperBtn.setBackgroundResource(0)
        stainlessBtn.setBackgroundResource(0)
        aluminiumBtn.setBackgroundResource(0)
        nickelBtn.setBackgroundResource(0)
        steelBtn.setBackgroundResource(0)
        toolsBtn.setBackgroundResource(0)
        chemicalBtn.setBackgroundResource(0)
        othersBtn.setBackgroundResource(0)

    }
}
