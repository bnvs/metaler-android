package com.example.metaler_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class ActivityMaterials : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materials)

        //탭바의 각 버튼에 맞는 액티비티로 이동하는 클릭 리스너
        homeBtn.setOnClickListener {
            val goToHome = Intent(this, ActivityHome::class.java)
            startActivity(goToHome)
        }
    }
}
