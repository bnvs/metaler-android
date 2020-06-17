package com.bnvs.metaler.view.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.ActivityHomeBinding
import com.bnvs.metaler.util.base.posts.BasePostsActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject

class ActivityHome : BasePostsActivity<ViewModelHome>() {

    private val TAG = "ActivityHome"

    override val viewModel: ViewModelHome by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityHome
        }

        setTransparentStatusBar()
        observeViewModel()

    }

    // 상태 바를 투명하게 하고, padding 을 조절한다
    private fun setTransparentStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //현재 액티비티 레이아웃의 기준이 되는 titleBarCard에 상태바 높이 만큼 top padding 을 줌 .
        titleBarCard.setPadding(0, statusBarHeight(this), 0, 0)
        Log.d(TAG, "상태바 높이? : ${statusBarHeight(this)}")

        //소프트키 올라온 높이만큼 전체 레이아웃 하단에 padding을 줌.
        wrapConstraintLayout.setPadding(0, 0, 0, softMenuHeight(this))
    }

    //상태바 높이 계산
    private fun statusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId)
        else 0
    }

    //하단 소프트키 높이 구함
    private fun softMenuHeight(context: Context): Int {
        val resourceId =
            context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

}
