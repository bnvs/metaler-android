package com.bnvs.metaler.view.addeditpost.postsecond.tagsuggest

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bnvs.metaler.R
import com.bnvs.metaler.databinding.DialogTagInputBinding
import org.koin.android.ext.android.inject

class DialogTagInput(
    private val type: String,
    private val setTag: String?,
    private val addTag: (tagInput: String) -> Unit
) : DialogFragment() {

    companion object {
        private const val TRIGGER_AUTO_COMPLETE = 200
        private const val AUTO_COMPLETE_DELAY = 50L
    }

    private val viewModel: ViewModelTagSuggest by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { it ->
            val builder = AlertDialog.Builder(it)
            val binding = DataBindingUtil.inflate<DialogTagInputBinding>(
                LayoutInflater.from(context),
                R.layout.dialog_tag_input, null, false
            ).apply {
                lifecycleOwner = it
                vm = viewModel
                tagInputEditTxt.let {
                    val tagSuggestHandler = Handler(Handler.Callback { msg ->
                        if (msg.what == TRIGGER_AUTO_COMPLETE) {
                            if (!it.text.isNullOrBlank()) {
                                viewModel.getTagSuggestions(type, it.text.toString())
                            }
                        }
                        false
                    })
                    if (!setTag.isNullOrBlank()) {
                        it.setText(setTag)
                    }
                    it.setAdapter(getHashTagSuggestAdapter())
                    it.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {}

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            tagSuggestHandler.run {
                                removeMessages(TRIGGER_AUTO_COMPLETE)
                                sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE, AUTO_COMPLETE_DELAY)
                            }
                        }
                    })
                }
            }
            builder
                .setTitle(getDialogTitle())
                .setView(binding.root)
                .setPositiveButton("확인") { dialog, _ ->
                    (dialog as Dialog).findViewById<EditText>(R.id.tagInputEditTxt)
                        .let { editText ->
                            val tagInput: String? = editText.text.toString()
                            if (!tagInput.isNullOrBlank()) {
                                addTag(tagInput)
                            }
                        }
                }
                .setNegativeButton("취소") { _, _ ->
                }
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun getHashTagSuggestAdapter(): HashTagSuggestAdapter {
        return HashTagSuggestAdapter(
            context ?: throw IllegalStateException("context cannot be null"),
            android.R.layout.simple_list_item_1
        )
    }

    private fun getDialogTitle(): String {
        return if (setTag.isNullOrBlank()) {
            "태그 추가"
        } else {
            "태그 수정"
        }
    }
}