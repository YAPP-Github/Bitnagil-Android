package com.threegap.bitnagil.presentation.screen.summary.model

import com.threegap.bitnagil.domain.activitylog.model.Badge
import com.threegap.bitnagil.domain.activitylog.model.BadgeType
import com.threegap.bitnagil.domain.activitylog.model.MonthlyBadge

data class SummaryBadgeUiModel(
    val badgeTitle: String,
    val badgeDescription: String,
    val badges: List<SummaryBadgeItemUiModel>,
)

data class SummaryBadgeItemUiModel(
    val type: BadgeType,
    val imageUrl: String,
    val acquired: Boolean,
)

fun MonthlyBadge.toUiModel(): SummaryBadgeUiModel =
    SummaryBadgeUiModel(
        badgeTitle = badgeTitle,
        badgeDescription = badgeDescription,
        badges = badges.map { it.toUiModel() },
    )

fun Badge.toUiModel(): SummaryBadgeItemUiModel =
    SummaryBadgeItemUiModel(
        type = type,
        imageUrl = imageUrl,
        acquired = acquired,
    )
