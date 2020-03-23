package com.example.metaler_android.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.metaler_android.materials.ActivityMaterials
import com.example.metaler_android.R
import com.example.metaler_android.data.homeposts.HomePost
import com.example.metaler_android.data.profile.Profile
import com.example.metaler_android.detail.ActivityDetail
import com.example.metaler_android.manufactures.ActivityManufactures
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_home_manufacture_rv.view.*
import kotlinx.android.synthetic.main.item_home_materials_rv.view.*

class ActivityHome : AppCompatActivity(), ContractHome.View {

    private val TAG = "ActivityHome"

    override lateinit var presenter: ContractHome.Presenter

    /**
     * 홈 탭에서 보여지는 재료/가공 게시물 리사이클러뷰 아이템에 달아줄 클릭리스너입니다
     * 아이템 클릭 시, 클릭한 게시물의 post_id 를 presenter 에 전달합니다.
     * */
    private var itemListener: HomePostItemListener = object : HomePostItemListener {
        override fun onHomePostClick(clickedHomePostId: Int) {
            presenter.openPostDetail(clickedHomePostId)
        }
    }

    private val materialsAdapter = HomePostAdapter("materials", ArrayList(0), itemListener)
    private val manufacturesAdapter = HomePostAdapter("manufactures", ArrayList(0), itemListener)
    private val materialsLayoutManager = LinearLayoutManager(this)
    private val manufacturesLayoutManager = LinearLayoutManager(this)

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
            layoutManager = materialsLayoutManager
        }

        // Set up manufactures recyclerView
        manufactureRV.apply {
            adapter = manufacturesAdapter
            layoutManager = manufacturesLayoutManager
        }

        // 홈 탭에서 보여줄 데이터 가져오기 시작
        // 탭 바 아이콘에 클릭 리스너 달아줌
        // 상태 바(배터리,와이파이 아이콘 표시되는 곳) 투명하게함
        presenter.run{
            start()
            setTapBar(this@ActivityHome)
            setStatusBar()
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    // 사용자 프로필을 보여준다
    override fun showProfile(profile: Profile) {
        Glide.with(this@ActivityHome)
            .load(profile.profile_image_url)
            .into(profileImg)
        profileNickname.text = profile.profile_nickname
        profileEmail.text = profile.profile_email
    }

    // 재료 리사이클러뷰를 보여준다
    override fun showMaterialsList(materials: List<HomePost>) {
        materialsAdapter.setHomePosts(materials)
        materialsAdapter.notifyDataSetChanged()
    }

    // 가공 리사이클러뷰를 보여준다
    override fun showManufacturesList(manufactures: List<HomePost>) {
        manufacturesAdapter.setHomePosts(manufactures)
        materialsAdapter.notifyDataSetChanged()
    }

    // 재료 탭으로 이동한다
    override fun showMaterialsUi() {
        Intent(this@ActivityHome, ActivityMaterials::class.java).also {
            startActivity(it)
        }
    }

    // 가공 탭으로 이동한다
    override fun showManufacturesUi() {
        Intent(this@ActivityHome, ActivityManufactures::class.java).also {
            startActivity(it)
        }
    }

    // 게시물 상세 내용 액티비티로 이동한다
    override fun showPostDetailUi(postId: Int) {
        Intent(this@ActivityHome, ActivityDetail::class.java)
            .apply { putExtra("postId", postId) }
            .also { startActivity(it) }

        overridePendingTransition(0,0)
    }

    // 하단 탭 바에 리스너를 추가한다
    override fun setTapBarListener(context: Context) {
        homeBtn.setOnClickListener {
            Intent(context, ActivityHome::class.java).also {
                startActivity(it)
            }
        }

        materialsBtn.setOnClickListener {
            Intent(context, ActivityMaterials::class.java).also {
                startActivity(it)
            }
        }

        manufactureBtn.setOnClickListener {
            Intent(context, ActivityManufactures::class.java).also {
                startActivity(it)
            }
        }

        /*bookmarkBtn.setOnClickListener {
            Intent(context, ActivityBookmarks::class.java).also {
                addFlags(it)
                startActivity(it)
            }
        }

        myPageBtn.setOnClickListener {
            Intent(context, ActivityMyPage::class.java).also {
                addFlags(it)
                startActivity(it)
            }
        }*/
    }

    // 상태 바를 투명하게 하고, padding 을 조절한다
    override fun setTransparentStatusBar() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //현재 액티비티 레이아웃의 기준이 되는 titleBarCard에 상태바 높이 만큼 top padding 을 줌 .
        titleBarCard.setPadding(0, statusBarHeight(this), 0, 0)
        Log.d(TAG,"상태바 높이? : ${statusBarHeight(this)}")

        //소프트키 올라온 높이만큼 전체 레이아웃 하단에 padding을 줌.
        wrapConstraintLayout.setPadding(0,0,0,softMenuHeight(this))
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

    /**
     * 홈 탭의 리사이클러뷰에 사용할 어댑터입니다.
     * postType 에 "materials" 또는 "manufactures" 문자열을 넣어
     * inflating 할 리사이클러뷰 아이템 뷰를 구분할 수 있습니다.
     * */
    private class HomePostAdapter(
        private val postType: String,
        private var homePosts: List<HomePost>,
        private val itemListener: HomePostItemListener
    ) : RecyclerView.Adapter<HomePostAdapter.ViewHolder>() {

        fun setHomePosts(list: List<HomePost>) {
            this.homePosts = list
        }

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

                var tags = ""
                for (tag in item.tags) {
                    tags += "#$tag "
                }

                when(postType) {
                    "materials" -> {
                        view.apply {
                            materialsTitle.text = item.title
                            materialsUserName.text = item.nickname
                            materialsDate.text = item.date
                            materialsTag.text = tags
                            setOnClickListener { itemListener.onHomePostClick(item.post_id) }
                        }
                    }
                    "manufactures" -> {
                        view.apply {
                            manufactureTitle.text = item.title
                            manufactureUserName.text = item.nickname
                            manufactureDate.text = item.date
                            manufactureTag.text = tags
                            setOnClickListener { itemListener.onHomePostClick(item.post_id) }
                        }
                    }
                }
            }
        }
    }

    private interface HomePostItemListener {
        fun onHomePostClick(clickedHomePostId: Int)
    }

}
