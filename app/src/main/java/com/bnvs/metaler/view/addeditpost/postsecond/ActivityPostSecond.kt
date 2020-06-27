package com.bnvs.metaler.view.addeditpost.postsecond

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.ActivityPostSecondBinding
import com.bnvs.metaler.util.base.BaseActivity
import com.bnvs.metaler.view.addeditpost.postsecond.tags.TagInputAdapter
import com.bnvs.metaler.view.posts.manufactures.ActivityManufactures
import com.bnvs.metaler.view.posts.materials.ActivityMaterials
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.activity_post_second.*
import org.koin.android.ext.android.inject

class ActivityPostSecond : BaseActivity<ViewModelPostSecond>() {

    companion object {
        private const val TAG = "ActivityPostSecond"
        private const val TRIGGER_AUTO_COMPLETE = 200
        private const val AUTO_COMPLETE_DELAY = 300L
    }

    override val viewModel: ViewModelPostSecond by inject()

    private lateinit var shopInputAdapter: HashTagSuggestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityPostSecondBinding>(
            this,
            R.layout.activity_post_second
        ).apply {
            vm = viewModel
            lifecycleOwner = this@ActivityPostSecond
            shopNameRv.let {
                it.layoutManager = getFlexBoxLayoutManager()
                it.adapter = TagInputAdapter(
                    getString(R.string.shop_name_guide),
                    tagClick = { position -> openTagSelectionDialog("store", position) },
                    addTagClick = { openAddTagDialog("store") }
                )
            }
            workRv.let {
                it.layoutManager = getFlexBoxLayoutManager()
                it.adapter = TagInputAdapter(
                    getString(R.string.work_guide),
                    tagClick = { position -> openTagSelectionDialog("work", position) },
                    addTagClick = { openAddTagDialog("work") }
                )
            }
            etcRv.let {
                it.layoutManager = getFlexBoxLayoutManager()
                it.adapter = TagInputAdapter(
                    getString(R.string.tag_input_guide),
                    tagClick = { position -> openTagSelectionDialog("etc", position) },
                    addTagClick = { openAddTagDialog("etc") }
                )
            }
        }
        observeViewModel()
        getPostId()
    }

    private fun getPostId() {
        val postId = intent.getIntExtra("POST_ID", -1)
        Log.d(TAG, "intent 로 들어온 postId : $postId")
        if (postId == -1) {
            viewModel.setPostId(null)
        } else {
            viewModel.setPostId(postId)
        }
    }

    private fun getFlexBoxLayoutManager(): FlexboxLayoutManager {
        return FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
        }
    }

    private fun openTagSelectionDialog(type: String, position: Int) {
        val array = arrayOf("태그 수정", "태그 삭제")
        AlertDialog.Builder(this@ActivityPostSecond)
            .setTitle("#${viewModel.getTagString(type, position)}")
            .setItems(array) { _, which ->
                when (array[which]) {
                    "태그 수정" -> openEditTagDialog(type, position)
                    "태그 삭제" -> openDeleteTagDialog(type, position)
                }
            }
            .show()
    }

    private fun openAddTagDialog(type: String) {
        AlertDialog.Builder(this@ActivityPostSecond)
            .setTitle("태그 추가")
            .setView(R.layout.dialog_tag_input)
            .setPositiveButton("확인") { dialog, which ->
                (dialog as Dialog).findViewById<EditText>(R.id.tagInputEditTxt).let {
                    val tagInput: String? = it.text.toString()
                    if (!tagInput.isNullOrBlank()) {
                        viewModel.addTag(type, tagInput)
                    }
                }
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    private fun openEditTagDialog(type: String, position: Int) {
        val content = layoutInflater.inflate(R.layout.dialog_tag_input, null).apply {
            viewModel.getTagString(type, position).let {
                if (it.isNotBlank()) {
                    this.findViewById<EditText>(R.id.tagInputEditTxt).setText(it)
                }
            }
        }
        AlertDialog.Builder(this@ActivityPostSecond)
            .setTitle("태그 수정")
            .setView(content)
            .setPositiveButton("확인") { dialog, _ ->
                (dialog as Dialog).findViewById<EditText>(R.id.tagInputEditTxt).let {
                    val tagInput: String? = it.text.toString()
                    if (!tagInput.isNullOrBlank()) {
                        viewModel.editTag(type, tagInput, position)
                    }
                }
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    private fun openDeleteTagDialog(type: String, position: Int) {
        AlertDialog.Builder(this@ActivityPostSecond)
            .setTitle("태그 삭제")
            .setMessage("\"#${viewModel.getTagString(type, position)}\" 태그를 삭제하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                viewModel.deleteTag(type, position)
            }
            .setNegativeButton("취소") { _, _ ->
            }
            .show()
    }

    /*override fun setShopNameTagInputAdapter() {
        shopInputAdapter =
            HashTagSuggestAdapter(
                this,
                android.R.layout.simple_list_item_1
            )
        shopNameInput.apply {
            setAdapter(shopInputAdapter)
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
                    if (!s.isNullOrBlank()) {
                        Log.d(
                            "shopNameInput",
                            "onTextChanged - start: $start, before: $before, count: $count"
                        )
                        Log.d(
                            "shopNameInput",
                            "onTextChanged 지금 첫 번째 글자는? ${shopNameInput.text.toString()[start]}"
                        )
                        Log.d(
                            "shopNameInput",
                            "onTextChanged 지금 작성중인 글자는? ${shopNameInput.text.toString().substring(
                                start,
                                start + count
                            )}"
                        )

                        shopNameInput.showDropDown()
                        presenter.getTagSuggestion(
                            1,
                            shopNameInput.text.toString().substring(start, start + count)
                        )
                    } else {
                        shopNameInput.dismissDropDown()
                    }
                }
            })
        }
    }*/

    fun setTagSuggestions(type: Int, tags: List<String>) {
        Log.d("태그 setTagSuggestions", "type: $type, tags: $tags")
        when (type) {
            1 -> {
                shopInputAdapter.setSuggests(tags)
                shopInputAdapter.notifyDataSetChanged()
            }
        }
    }

    fun showEmptyTagsDialog() {
        AlertDialog.Builder(this@ActivityPostSecond)
            .setTitle("알림")
            .setMessage("필수 태그를 입력해 주세요")
            .setPositiveButton("확인") { _, _ ->
            }
            .show()
    }

    fun showInvalidateTagDialog() {
        AlertDialog.Builder(this@ActivityPostSecond)
            .setTitle("알림")
            .setMessage("'#태그' 형식의 띄어쓰기 없는 태그만 입력 가능합니다")
            .setPositiveButton("확인") { _, _ ->
            }
            .show()
    }

    fun finishAddEditUi(categoryType: String) {
        when (categoryType) {
            "MATERIALS" -> {
                Intent(this, ActivityMaterials::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(this)
                }
            }
            "MANUFACTURES" -> {
                Intent(this, ActivityManufactures::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(this)
                }
            }
        }
    }

    fun showAddPostFailureToast(errorMessage: String) {
        makeToast(errorMessage)
    }

    fun showEditPostFailureToast(errorMessage: String) {
        makeToast(errorMessage)
    }

    private fun initClickListener() {
        backBtn.setOnClickListener { finish() }
        completeBtn.setOnClickListener {
            /* val tags = JSONObject().apply {
                 put("store", shopNameInput.text.toString())
                 put("work", workInput.text.toString())
                 put("etc", tagInput.text.toString())
             }
             presenter.finishAddEditPost(tags)*/
        }

    }

}
