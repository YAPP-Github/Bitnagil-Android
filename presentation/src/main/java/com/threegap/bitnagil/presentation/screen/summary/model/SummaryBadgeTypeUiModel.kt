package com.threegap.bitnagil.presentation.screen.summary.model

import com.threegap.bitnagil.domain.activitylog.model.BadgeType

sealed class SummaryBadgeTypeUiModel(
    val isReserved: Boolean = false
) {
    data object MotivationExpert : SummaryBadgeTypeUiModel()
    data object CheckExpert : SummaryBadgeTypeUiModel()
    data object OutingExpert : SummaryBadgeTypeUiModel()
    data object ReserveExpert : SummaryBadgeTypeUiModel(isReserved = true)
    data object Unknown : SummaryBadgeTypeUiModel(isReserved = true)
}

fun BadgeType.toUiModel(): SummaryBadgeTypeUiModel =
    when (this) {
        BadgeType.MOTIVATION_EXPERT -> SummaryBadgeTypeUiModel.MotivationExpert
        BadgeType.CHECK_EXPERT -> SummaryBadgeTypeUiModel.CheckExpert
        BadgeType.OUTING_EXPERT -> SummaryBadgeTypeUiModel.OutingExpert
        BadgeType.RESERVE_EXPERT -> SummaryBadgeTypeUiModel.ReserveExpert
        BadgeType.UNKNOWN -> SummaryBadgeTypeUiModel.Unknown
    }
