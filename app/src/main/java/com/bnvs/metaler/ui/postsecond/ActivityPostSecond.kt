package com.bnvs.metaler.ui.postsecond

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import kotlinx.android.synthetic.main.activity_post_second.*
import org.json.JSONObject

class ActivityPostSecond : AppCompatActivity(), ContractPostSecond.View {

    companion object {
        private const val TAG = "ActivityPostSecond"
        private const val TRIGGER_AUTO_COMPLETE = 200
        private const val AUTO_COMPLETE_DELAY = 300L
    }

    override lateinit var presenter: ContractPostSecond.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_second)

        val categoryType = intent.getStringExtra("CATEGORY_TYPE")
        val postIdString = intent.getStringExtra("POST_ID")
        var postId: Int? = null
        if (postIdString != null) {
            postId = postIdString.toInt()
        }

        presenter = PresenterPostSecond(categoryType!!, postId, this)

        presenter.run {
            getAddEditPostRequest(intent)
            start()
        }

    }

    override fun showManufactureWorkTagInput(b: Boolean) {
        if (b) {
            work.visibility = View.VISIBLE
        } else {
            work.visibility = View.GONE
        }
    }

    override fun setShopNameTagInputAdapter() {
        val adapter = HashTagSuggestAdapter(this, android.R.layout.simple_list_item_1)
        val shopNameHandler = Handler(Handler.Callback { msg ->
            if (msg.what == TRIGGER_AUTO_COMPLETE) {
                if (!shopNameInput.text.isNullOrEmpty()) {
                    // 태그 검색 추천 api 호출
                }
            }
            false
        })
        shopNameInput.apply {
            setAdapter(adapter)
            setTokenizer(SpaceTokenizer())
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    shopNameHandler.removeMessages(TRIGGER_AUTO_COMPLETE)
                    shopNameHandler.sendEmptyMessageDelayed(
                        TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY
                    )
                }
            })
        }
    }

    override fun setWorkTagInputAdapter() {
        val adapter = HashTagSuggestAdapter(this, android.R.layout.simple_list_item_1)
        val workHandler = Handler(Handler.Callback { msg ->
            if (msg.what == TRIGGER_AUTO_COMPLETE) {
                if (!workInput.text.isNullOrEmpty()) {
                    // 태그 검색 추천 api 호출
                }
            }
            false
        })
        workInput.apply {
            setAdapter(adapter)
            setTokenizer(SpaceTokenizer())
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    workHandler.removeMessages(TRIGGER_AUTO_COMPLETE)
                    workHandler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY)
                }
            })
        }
    }

    override fun setTagInputAdapter() {
        val adapter = HashTagSuggestAdapter(this, android.R.layout.simple_list_item_1)
        val tagHandler = Handler(Handler.Callback { msg ->
            if (msg.what == TRIGGER_AUTO_COMPLETE) {
                if (!tagInput.text.isNullOrEmpty()) {
                    // 태그 검색 추천 api 호출
                }
            }
            false
        })
        tagInput.apply {
            setAdapter(adapter)
            setTokenizer(SpaceTokenizer())
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    tagHandler.removeMessages(TRIGGER_AUTO_COMPLETE)
                    tagHandler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY)
                }
            })
        }
    }

    override fun setShopNameInput(tags: String) {
        shopNameInput.setText(tags)
    }

    override fun setWorkInput(tags: String) {
        workInput.setText(tags)
    }

    override fun setTagInput(tags: String) {
        tagInput.setText(tags)
    }

    override fun showEmptyTagsDialog() {
        AlertDialog.Builder(this@ActivityPostSecond)
            .setTitle("알림")
            .setMessage("필수 태그를 입력해 주세요")
            .setPositiveButton("확인") { _, _ ->
            }
            .show()
    }

    override fun showInvalidateTagDialog() {
        AlertDialog.Builder(this@ActivityPostSecond)
            .setTitle("알림")
            .setMessage("'#태그' 형식의 띄어쓰기 없는 태그만 입력 가능합니다")
            .setPositiveButton("확인") { _, _ ->
            }
            .show()
    }
}
