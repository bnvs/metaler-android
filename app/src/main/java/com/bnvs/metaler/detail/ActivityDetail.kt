package com.bnvs.metaler.detail

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
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

//        registerForContextMenu(moreBtn)

    }

    override fun showPostDetail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showComments() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.modify ->{
                //수정버튼 눌렀을 때, presenter 가 할 일 추가하기
                Toast.makeText(applicationContext, "modify code", Toast.LENGTH_LONG).show()//test용 토스트메세지
                return true
            }
            R.id.delete ->{
                //삭제버튼 눌렀을 때, presenter 가 할 일 추가하기
                Toast.makeText(applicationContext, "delete code", Toast.LENGTH_LONG).show()//test용 토스트메세지
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun showMenuDialog() {
        registerForContextMenu(moreBtn)
        openContextMenu(moreBtn)
        unregisterForContextMenu(moreBtn)
    }

    private fun initClickListeners() {
        setTitleBarButtons()
        setRatingButtons()

        moreBtn.setOnClickListener { presenter.openMenu() }
    }

    private fun setTitleBarButtons() {
        // 백버튼, 북마크, more 버튼 클릭 리스너 달아주기

    }

    private fun setRatingButtons() {
        // 좋아요, 싫어요 버튼 클릭 리스너 달아주기

    }

    override fun refreshRatingButtons() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
