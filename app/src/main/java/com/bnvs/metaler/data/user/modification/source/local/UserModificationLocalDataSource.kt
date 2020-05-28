package com.bnvs.metaler.data.user.modification.source.local

import com.bnvs.metaler.data.user.modification.model.Nickname
import com.bnvs.metaler.data.user.modification.model.TermsAgreements

interface UserModificationLocalDataSource {

    fun modifyNickname(
        request: Nickname,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    fun saveTermsAgreements(
        agreements: TermsAgreements
    )

    fun getTermsAgreements(
        onSuccess: (agreements: TermsAgreements) -> Unit,
        onFailure: () -> Unit
    )
}