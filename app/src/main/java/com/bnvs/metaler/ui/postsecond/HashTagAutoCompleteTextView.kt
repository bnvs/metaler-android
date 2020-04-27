package com.bnvs.metaler.ui.postsecond

import android.content.Context
import android.util.AttributeSet
import android.widget.MultiAutoCompleteTextView

/**
 * 무조건 #이 붙은 태그들 (띄어쓰기 미포함)만 들어갈 수 있도록
 * 커스텀한 자동완성 텍스트뷰이다
 * */

class HashTagAutoCompleteTextView : MultiAutoCompleteTextView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        val string = this.text.toString()

        if (string.isNotEmpty() && selStart > 0) {

            if (selStart > 1) {
                val selChar = string[selStart - 1]
                val beforeSelChar = string[selStart - 2]

                if (selChar == ' ' && beforeSelChar == '#') {
                    if (selStart == string.length) {
                        val tag = string.substring(0, string.length - 1)
                        this.setText(tag)
                        this.setSelection(this.text.length)
                    } else {
                        val tag = "${string.substring(0, selStart - 1)}${string.substring(
                            selStart,
                            string.length
                        )}"
                        this.setText(tag)
                        this.setSelection(this.text.length)
                    }

                }

                if (selChar != '#' && beforeSelChar == ' ') {
                    if (selStart == string.length) {
                        val tag = "${string.substring(0, string.length - 1)}#"
                        this.setText(tag)
                        this.setSelection(this.text.length)
                    } else {
                        val tag = "${string.substring(0, selStart - 1)}#${string.substring(
                            selStart + 1,
                            string.length
                        )}"
                        this.setText(tag)
                        this.setSelection(this.text.length)
                    }
                }

                if (selChar != ' ' && beforeSelChar != ' ') {
                    if (selStart < string.length) {
                        val afterSelChar = string[selStart]
                        if (afterSelChar == '#') {
                            val tag = "${string.substring(0, selStart)} ${string.substring(
                                selStart,
                                string.length
                            )}"
                            this.setText(tag)
                            this.setSelection(this.text.length)
                        }
                    }
                }


                if (selChar == '#' && beforeSelChar != ' ') {
                    if (selStart == string.length) {
                        if (beforeSelChar != '#') {
                            val tag = "${string.substring(0, string.length - 1)} #"
                            this.setText(tag)
                            this.setSelection(this.text.length)
                        } else {
                            val tag = string.substring(0, string.length - 1)
                            this.setText(tag)
                            this.setSelection(this.text.length)
                        }

                    } else {
                        if (beforeSelChar != '#') {
                            val tag = "${string.substring(
                                0,
                                selStart - 1
                            )} #${string.substring(selStart + 1, string.length)}"
                            this.setText(tag)
                            this.setSelection(this.text.length)
                        } else {
                            val tag = "${string.substring(0, selStart - 1)}${string.substring(
                                selStart,
                                string.length
                            )}"
                            this.setText(tag)
                            this.setSelection(this.text.length)
                        }

                    }
                }

                if (selChar == ' ' && beforeSelChar != ' ') {
                    if (selStart < string.length) {
                        val afterSelChar = string[selStart]
                        if (afterSelChar == ' ') {
                            val tag = "${string.substring(0, selStart - 1)}${string.substring(
                                selStart,
                                string.length
                            )}"
                            this.setText(tag)
                            this.setSelection(this.text.length)
                        } else if (afterSelChar != '#' && beforeSelChar != '#') {
                            val tag = "${string.substring(0, selStart)}#${string.substring(
                                selStart,
                                string.length
                            )}"
                            this.setText(tag)
                            this.setSelection(this.text.length)
                        }
                    }
                }

            }
        }
    }
}