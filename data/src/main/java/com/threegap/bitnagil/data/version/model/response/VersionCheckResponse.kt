package com.threegap.bitnagil.data.version.model.response

import com.threegap.bitnagil.domain.version.model.UpdateRequirement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionCheckResponse(
    @SerialName("forceUpdateYn")
    val forceUpdateYn: Boolean,
)

fun VersionCheckResponse.toDomain() =
    UpdateRequirement(
        isForced = this.forceUpdateYn,
    )
