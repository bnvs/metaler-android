package com.bnvs.metaler.view.addeditpost.postsecond

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.ActivityPostSecondBinding
import com.bnvs.metaler.util.base.BaseActivity
import com.bnvs.metaler.view.addeditpost.postsecond.tags.TagInputAdapter
import com.bnvs.metaler.view.posts.manufactures.ActivityManufactures
import com.bnvs.metaler.view.posts.materials.ActivityMaterials
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import org.koin.android.ext.android.inject

class ActivityPostSecond : BaseActivity<ViewModelPostSecond>() {

    companion object {
        private const val TAG = "ActivityPostSecond"
        private const val TRIGGER_AUTO_COMPLETE = 200
        private const val AUTO_COMPLETE_DELAY = 300L
    }

    override val viewModel: ViewModelPostSecond by inject()
    private val viewModelTagSuggest: ViewModelTagSuggest by inject()

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
                    tagClick = { position -> viewModel.openTagSelectionDialog("store", position) },
                    addTagClick = { viewModel.openAddTagDialog("store") }
                )
            }
            workRv.let {
                it.layoutManager = getFlexBoxLayoutManager()
                it.adapter = TagInputAdapter(
                    getString(R.string.work_guide),
                    tagClick = { position -> viewModel.openTagSelectionDialog("work", position) },
                    addTagClick = { viewModel.openAddTagDialog("work") }
                )
            }
            etcRv.let {
                it.layoutManager = getFlexBoxLayoutManager()
                it.adapter = TagInputAdapter(
                    getString(R.string.tag_input_guide),
                    tagClick = { position -> viewModel.openTagSelectionDialog("etc", position) },
                    addTagClick = { viewModel.openAddTagDialog("etc") }
                )
            }
        }
        observeViewModel()
        getPostId()
    }

    override fun observeViewModel() {
        super.observeViewModel()
        observeBackToPostFirstActivity()
        observeFinishAddEditPostActivity()
        observeOpenTagSelectionDialog()
        observeOpenAddTagDialog()
        observeOpenEditTagDialog()
        observeOpenDeleteTagDialog()
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

    private fun observeBackToPostFirstActivity() {
        viewModel.backToPostFirstActivity.observe(
            this,
            Observer { backToPostFirst ->
                if (backToPostFirst) {
                    finish()
                }
            }
        )
    }

    private fun observeFinishAddEditPostActivity() {
        viewModel.finishAddEditPostActivity.observe(
            this,
            Observer { categoryType ->
                if (categoryType.isNotBlank()) {
                    finishAddEditUi(categoryType)
                }
            }
        )
    }

    private fun observeOpenTagSelectionDialog() {
        viewModel.openTagSelectionDialog.observe(
            this,
            Observer { openDialog ->
                if (openDialog.isNotEmpty()) {
                    openTagSelectionDialog(
                        openDialog["type"] as String,
                        openDialog["position"] as Int
                    )
                }
            }
        )
    }

    private fun observeOpenAddTagDialog() {
        viewModel.openAddTagDialog.observe(
            this,
            Observer { openDialog ->
                if (openDialog.isNotEmpty()) {
                    openAddTagDialog(openDialog["type"] as String)
                }
            }
        )
    }

    private fun observeOpenEditTagDialog() {
        viewModel.openEditTagDialog.observe(
            this,
            Observer { openDialog ->
                if (openDialog.isNotEmpty()) {
                    openEditTagDialog(
                        openDialog["type"] as String,
                        openDialog["position"] as Int
                    )
                }
            }
        )
    }

    private fun observeOpenDeleteTagDialog() {
        viewModel.openDeleteTagDialog.observe(
            this,
            Observer { openDialog ->
                if (openDialog.isNotEmpty()) {
                    openDeleteTagDialog(
                        openDialog["type"] as String,
                        openDialog["position"] as Int
                    )
                }
            }
        )
    }

    private fun openTagSelectionDialog(type: String, position: Int) {
        val array = arrayOf("태그 수정", "태그 삭제")
        AlertDialog.Builder(this@ActivityPostSecond)
            .setTitle("#${viewModel.getTagString(type, position)}")
            .setItems(array) { _, which ->
                when (array[which]) {
                    "태그 수정" -> viewModel.openEditTagDialog(type, position)
                    "태그 삭제" -> viewModel.openDeleteTagDialog(type, position)
                }
            }
            .show()
    }

    private fun openAddTagDialog(type: String) {
        DialogTagInput(viewModelTagSuggest, type, addTag = { tag ->
            viewModel.addTag(type, tag)
        }).show(supportFragmentManager, "tagInput")
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

    private fun finishAddEditUi(categoryType: String) {
        when (categoryType) {
            "materials" -> {
                Intent(this, ActivityMaterials::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }.also { startActivity(it) }
            }
            "manufacture" -> {
                Intent(this, ActivityManufactures::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }.also { startActivity(it) }
            }
        }
    }

}
