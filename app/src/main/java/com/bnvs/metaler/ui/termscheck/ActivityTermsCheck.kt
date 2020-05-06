package com.bnvs.metaler.ui.termscheck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bnvs.metaler.R
import com.bnvs.metaler.data.token.source.repository.TokenRepository
import kotlinx.android.synthetic.main.activity_terms_check.*

class ActivityTermsCheck : AppCompatActivity() {

    private lateinit var tokenRepository: TokenRepository
    private val url = "http://metaler.kr/users/terms"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_check)

        tokenRepository = TokenRepository(this)
        tokenRepository.getAccessToken(
            onTokenLoaded = { token ->
                val header = mapOf("Authorization" to token.access_token)
                termsCheckWebView.loadUrl(url, header)
            },
            onTokenNotExist = {
                finish()
            }
        )
    }
}