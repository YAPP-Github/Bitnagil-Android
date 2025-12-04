package com.threegap.bitnagil.data.auth.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WithdrawalReasonRequest(
    @SerialName("reasonOfWithdrawal") val reasonOfWithdrawal: String,
)
