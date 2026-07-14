package com.threegap.bitnagil.domain.activitylog.model

import java.time.LocalDateTime

data class Badge(
    val type: BadgeType,
    val title: String,
    val description: String,
    val imageUrl: String,
    val acquiredAt: LocalDateTime?,
) {
    val acquired: Boolean get() = acquiredAt != null
}
