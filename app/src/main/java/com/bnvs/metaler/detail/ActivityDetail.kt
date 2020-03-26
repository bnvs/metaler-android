package com.bnvs.metaler.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bnvs.metaler.R
import kotlinx.android.synthetic.main.activity_detail.*


//재료탭, 가공탭의 상세페이지 레이아웃이 같기 떄문에 같이 사용한다.
class ActivityDetail : AppCompatActivity(), ContractDetail.View {

    private val TAG = "ActivityDetail"

    override lateinit var presenter: ContractDetail.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Create the presenter
        presenter = PresenterDetail(this, this)

        // Set up Buttons
        initClickListeners()

        // 재료 탭 presenter 시작
        presenter.run {
            start()
        }
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


    private fun initClickListeners() {
        setTitleBarButtons()
        setRatingButtons()
    }

    private fun setTitleBarButtons() {
        // 백버튼, 북마크, more 버튼 클릭 리스너 달아주기

    }

    private fun setRatingButtons() {
        // 좋아요, 싫어요 버튼 클릭 리스너 달아주기

    }



}
