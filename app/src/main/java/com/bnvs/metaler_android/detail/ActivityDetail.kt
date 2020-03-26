package com.bnvs.metaler_android.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bnvs.metaler_android.R


//재료탭, 가공탭의 상세페이지 레이아웃이 같기 떄문에 같이 사용한다.
class ActivityDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}
