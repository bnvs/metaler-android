package com.bnvs.metaler.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bnvs.metaler.R


//재료탭, 가공탭의 상세페이지 레이아웃이 같기 떄문에 같이 사용한다.
class ActivityDetail : AppCompatActivity(), ContractDetail.View {

    private val TAG = "ActivityDetail"

    override lateinit var presenter: ContractDetail.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    override fun showPostDetail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showComments() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMenuDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
