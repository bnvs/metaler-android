package com.example.metaler_android.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.metaler_android.materials.ActivityMaterials
import com.example.metaler_android.R
import com.example.metaler_android.data.homepost.HomePost
import com.example.metaler_android.data.profile.Profile
import com.example.metaler_android.detail.ActivityDetail
import com.example.metaler_android.manufactures.ActivityManufactures
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_home_manufacture_rv.view.*
import kotlinx.android.synthetic.main.item_home_materials_rv.view.*

class ActivityHome : AppCompatActivity(), ContractHome.View {

    val TAG = "ActivityHome"

    override lateinit var presenter: ContractHome.Presenter

    private var itemListener: HomePostItemListener = object : HomePostItemListener {
        override fun onHomePostClick(clickedHomePostId: Int) {
            presenter.openPostDetail(clickedHomePostId)
        }
    }

    private val materialsAdapter = HomePostAdapter("materials", ArrayList(0), itemListener)
    private val manufacturesAdapter = HomePostAdapter("manufactures", ArrayList(0), itemListener)
    private val linearLayoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Create the presenter
        presenter = PresenterHome(
            this@ActivityHome,
            this@ActivityHome
        )

        // Set up Buttons
        materialsMoreBtn.setOnClickListener { presenter.openMaterials() }
        manufactureMoreBtn.setOnClickListener { presenter.openManufactures() }

        // Set up materials recyclerView
        materialsRV.apply {
            adapter = materialsAdapter
            layoutManager = linearLayoutManager
        }

        // Set up manufactures recyclerView
        manufactureRV.apply {
            adapter = manufacturesAdapter
            layoutManager = linearLayoutManager
        }

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

    override fun showProfile(profile: Profile) {
        profileNickname.text = profile.profile_nickname
        profileEmail.text = profile.profile_email
    }

    override fun showMaterialsList(materials: List<HomePost>) {
        materialsAdapter.homePosts = materials
        materialsAdapter.notifyDataSetChanged()
    }

    override fun showManufacturesList(manufactures: List<HomePost>) {
        manufacturesAdapter.homePosts = manufactures
        materialsAdapter.notifyDataSetChanged()
    }

    override fun showMaterialsUi() {
        val intent = Intent(this@ActivityHome, ActivityMaterials::class.java)
        addFlags(intent)
        startActivity(intent)
    }

    override fun showManufacturesUi() {
        val intent = Intent(this@ActivityHome, ActivityManufactures::class.java)
        addFlags(intent)
        startActivity(intent)
    }

    override fun showPostDetailUi(postId: Int) {
        val intent = Intent(this@ActivityHome, ActivityDetail::class.java).apply {
            putExtra("postId", postId)
        }
        addFlags(intent)
        startActivity(intent)
        overridePendingTransition(0,0)
    }

    // TODO : flag doesn't work. have to fix it
    private fun addFlags(intent: Intent) {
        intent.flags.apply {
            Intent.FLAG_ACTIVITY_NO_ANIMATION
            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
    }

    private class HomePostAdapter(
        private val postType: String,
        var homePosts: List<HomePost>,
        private val itemListener: HomePostItemListener
    ) : RecyclerView.Adapter<HomePostAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            lateinit var inflatedView: View
            when(postType) {
                "materials" -> {
                    inflatedView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_home_materials_rv, parent, false)
                }
                "manufactures" -> {
                    inflatedView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_home_manufacture_rv, parent, false)
                }
            }
            return ViewHolder(inflatedView)
        }

        override fun getItemCount(): Int {
            return homePosts.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(homePosts[position])
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var view: View = itemView

            fun bind(item: HomePost) {
                when(postType) {
                    "materials" -> {
                        view.materialsTitle.text = item.title
                        view.materialsUserName.text = item.nickname
                        view.materialsDate.text = item.date
                        view.setOnClickListener { itemListener.onHomePostClick(item.post_id) }
                    }
                    "manufactures" -> {
                        view.manufactureTitle.text = item.title
                        view.manufactureUserName.text = item.nickname
                        view.manufactureDate.text = item.date
                        view.setOnClickListener { itemListener.onHomePostClick(item.post_id) }
                    }
                }
            }
        }
    }

    interface HomePostItemListener {
        fun onHomePostClick(clickedHomePostId: Int)
    }

}
