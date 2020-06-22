package com.bnvs.metaler.view.addeditpost.postsecond

import android.content.Context
import android.util.AttributeSet
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast

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
                        this.apply {
                            setText(tag)
                            setSelection(this.text.length)
                            Toast.makeText(context, "태그 입력시 '#'을 태그 앞에 붙여주세요", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        val tag = "${string.substring(0, selStart - 1)}${string.substring(
                            selStart,
                            string.length
                        )}"
                        this.apply {
                            setText(tag)
                            setSelection(this.text.length)
                            Toast.makeText(context, "태그 입력시 '#'을 태그 앞에 붙여주세요", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }

                if (selChar != '#' && beforeSelChar == ' ') {
                    if (selStart == string.length) {
                        val tag = "${string.substring(0, string.length - 1)}#"
                        this.apply {
                            setText(tag)
                            setSelection(this.text.length)
                            Toast.makeText(context, "태그 입력시 '#'을 태그 앞에 붙여주세요", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        val tag = "${string.substring(0, selStart - 1)}#${string.substring(
                            selStart + 1,
                            string.length
                        )}"
                        this.apply {
                            setText(tag)
                            setSelection(this.text.length)
                            Toast.makeText(context, "태그 입력시 '#'을 태그 앞에 붙여주세요", Toast.LENGTH_SHORT)
                                .show()
                        }
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
                            this.apply {
                                setText(tag)
                                setSelection(this.text.length)
                                Toast.makeText(
                                    context,
                                    "태그 입력시 '#'을 태그 앞에 붙여주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }


                if (selChar == '#' && beforeSelChar != ' ') {
                    if (selStart == string.length) {
                        if (beforeSelChar != '#') {
                            val tag = "${string.substring(0, string.length - 1)} #"
                            this.apply {
                                setText(tag)
                                setSelection(this.text.length)
                                Toast.makeText(
                                    context,
                                    "태그 입력시 '#'을 태그 앞에 붙여주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            val tag = string.substring(0, string.length - 1)
                            this.apply {
                                setText(tag)
                                setSelection(this.text.length)
                                Toast.makeText(
                                    context,
                                    "태그 입력시 '#'을 태그 앞에 붙여주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    } else {
                        if (beforeSelChar != '#') {
                            val tag = "${string.substring(0, selStart - 1)} #${string.substring(
                                selStart,
                                string.length
                            )}"
                            this.apply {
                                setText(tag)
                                setSelection(this.text.length)
                                Toast.makeText(
                                    context,
                                    "태그 입력시 '#'을 태그 앞에 붙여주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            val tag = "${string.substring(0, selStart - 1)}${string.substring(
                                selStart,
                                string.length
                            )}"
                            this.apply {
                                setText(tag)
                                setSelection(this.text.length)
                                Toast.makeText(
                                    context,
                                    "태그 입력시 '#'을 태그 앞에 붙여주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }
                }

                if (selChar == '#' && beforeSelChar == ' ') {
                    if (selStart < string.length) {
                        val afterSelChar = string[selStart]
                        if (afterSelChar == ' ') {
                            val tag = "${string.substring(0, selStart - 2)}${string.substring(
                                selStart,
                                string.length
                            )}"
                            this.apply {
                                setText(tag)
                                setSelection(this.text.length)
                                Toast.makeText(
                                    context,
                                    "태그 입력시 '#'을 태그 앞에 붙여주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
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
                            this.apply {
                                setText(tag)
                                setSelection(this.text.length)
                                Toast.makeText(
                                    context,
                                    "태그 입력시 '#'을 태그 앞에 붙여주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else if (afterSelChar != '#' && beforeSelChar != '#') {
                            val tag = "${string.substring(0, selStart)}#${string.substring(
                                selStart,
                                string.length
                            )}"
                            this.apply {
                                setText(tag)
                                setSelection(this.text.length)
                                Toast.makeText(
                                    context,
                                    "태그 입력시 '#'을 태그 앞에 붙여주세요",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

            } else {
                val selChar = string[selStart - 1]
                if (selChar != '#') {
                    if (selStart == string.length) {
                        val tag = "#"
                        this.apply {
                            setText(tag)
                            setSelection(this.text.length)
                            Toast.makeText(context, "태그 입력시 '#'을 태그 앞에 붙여주세요", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        val tag = "#$string"
                        this.apply {
                            setText(tag)
                            setSelection(this.text.length)
                            Toast.makeText(context, "태그 입력시 '#'을 태그 앞에 붙여주세요", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}