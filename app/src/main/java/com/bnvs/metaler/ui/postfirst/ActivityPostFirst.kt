package com.bnvs.metaler.ui.postfirst

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bnvs.metaler.R
import kotlinx.android.synthetic.main.activity_post_first.*

class ActivityPostFirst : AppCompatActivity(), ContractPostFirst.View {

    private val TAG = "ActivityPostFirst"

    override lateinit var presenter: ContractPostFirst.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_first)

        val categoryType = intent.getStringExtra("CATEGORY_TYPE")
        val postId = intent.getIntExtra("POST_ID", 0)

        presenter = PresenterPostFirst(categoryType, postId, this)

        presenter.run {

        }
    }

    override fun showCategories() {
        materialsCategory.visibility = View.VISIBLE
    }

    override fun setCategory(category: String) {
        categoryTxt.text = category
    }

    override fun setTitle(title: String) {
        titleInput.setText(title)
    }

    override fun setPrice(price: Int) {
        priceInput.setText(price)
    }

    override fun setCardButton() {
        setButtonEnabled(cardBtn, true)
        setButtonEnabled(cashBtn, false)
    }

    override fun setCashButton() {
        setButtonEnabled(cardBtn, false)
        setButtonEnabled(cashBtn, true)
    }

    private fun setButtonEnabled(button: TextView, b: Boolean) {
        if (b) {
            button.setBackgroundResource(R.drawable.job_btn_purple_rounding_border)
            button.setTextColor(ContextCompat.getColor(this, R.color.colorPurple))
        } else {
            button.setBackgroundResource(R.drawable.job_btn_lightgrey_rounding_border)
            button.setTextColor(ContextCompat.getColor(this, R.color.colorLightGrey))
        }
    }

    override fun setImageGuideText() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setImages() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPostDetailLoadFailedDialog(errorMessage: String) {

    }

    override fun showImageDeleteDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyTitleDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyPriceDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyPriceTypeDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmptyContentsDialog() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPostSecondUi() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
