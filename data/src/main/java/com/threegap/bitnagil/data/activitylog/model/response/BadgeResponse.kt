package com.threegap.bitnagil.data.activitylog.model.response

import com.threegap.bitnagil.domain.activitylog.model.Badge
import com.threegap.bitnagil.domain.activitylog.model.BadgeType
import com.threegap.bitnagil.domain.activitylog.model.MonthlyBadge
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class MonthlyBadgeResponse(
    @SerialName("badgeTitle")
    val badgeTitle: String,
    @SerialName("badgeDescription")
    val badgeDescription: String,
    @SerialName("badges")
    val badges: List<BadgeResponse>,
)

@Serializable
data class BadgeResponse(
    @SerialName("badgeType")
    val badgeType: BadgeType,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("acquiredAt")
    val acquiredAt: String?,
)

fun MonthlyBadgeResponse.toDomain(): MonthlyBadge =
    MonthlyBadge(
        badgeTitle = badgeTitle,
        badgeDescription = badgeDescription,
        badges = badges.map { it.toDomain() },
    )

fun BadgeResponse.toDomain(): Badge =
    Badge(
        type = badgeType,
        imageUrl = imageUrl,
        acquiredAt = acquiredAt?.let { LocalDateTime.parse(it) },
    )
