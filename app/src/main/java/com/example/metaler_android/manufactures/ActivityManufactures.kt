package com.example.metaler_android.manufactures

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.metaler_android.R
import com.example.metaler_android.home.ActivityHome
import com.example.metaler_android.materials.ActivityMaterials
import kotlinx.android.synthetic.main.activity_manufacture.*

class ActivityManufactures : AppCompatActivity(), ContractManufactures.View {

    private val TAG = "ActivityManufactures"

    override lateinit var presenter: ContractManufactures.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manufacture)

        presenter = PresenterManufactures()
    }

    override fun showPosts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPostDetailUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchTags() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearSearchTagBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteSearchTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // 하단 탭 바에 리스너를 추가한다
    override fun setTapBarListener(context: Context) {
        homeIcon.setOnClickListener {
            Intent(context, ActivityHome::class.java).also {
                startActivity(it)
            }
        }

        materialsIcon.setOnClickListener {
            Intent(context, ActivityMaterials::class.java).also {
                startActivity(it)
            }
        }

        manufactureIcon.setOnClickListener {
            Intent(context, ActivityManufactures::class.java).also {
                startActivity(it)
            }
        }

        /*bookmarkIcon.setOnClickListener {
            Intent(context, ActivityBookmarks::class.java).also {
                addFlags(it)
                startActivity(it)
            }
        }

        myPageIcon.setOnClickListener {
            Intent(context, ActivityMyPage::class.java).also {
                addFlags(it)
                startActivity(it)
            }
        }*/
    }
}
