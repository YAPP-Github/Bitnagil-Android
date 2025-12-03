package com.threegap.bitnagil.data.auth.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WithdrawalReasonResponse(
    @SerialName("reasonOfWithdrawal") val reasonOfWithdrawal: String,
)
