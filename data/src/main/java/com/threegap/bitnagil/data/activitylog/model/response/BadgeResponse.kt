package com.threegap.bitnagil.data.activitylog.model.response

import com.threegap.bitnagil.domain.activitylog.model.Badge
import com.threegap.bitnagil.domain.activitylog.model.BadgeType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class BadgeResponse(
    @SerialName("badgeType")
    val badgeType: BadgeType,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("acquiredAt")
    val acquiredAt: String?,
)

fun BadgeResponse.toDomain(): Badge =
    Badge(
        type = badgeType,
        title = title,
        description = description,
        imageUrl = imageUrl,
        acquiredAt = acquiredAt?.let { LocalDateTime.parse(it) },
    )
