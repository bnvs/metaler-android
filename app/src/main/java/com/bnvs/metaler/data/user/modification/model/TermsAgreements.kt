package com.bnvs.metaler.data.user.modification.model

data class TermsAgreements(
    val service_agree: Boolean,
    val privacy_agree: Boolean,
    val additional_agree: Boolean,
    val advertising_agree: Boolean
)