package com.threegap.bitnagil.presentation.screen.summary.model

import com.threegap.bitnagil.domain.activitylog.model.Badge

data class SummaryBadgeUiModel(
    val title: String,
    val description: String,
    val imageUrl: String,
    val acquired: Boolean,
)

fun Badge.toUiModel(): SummaryBadgeUiModel =
    SummaryBadgeUiModel(
        title = title,
        description = description,
        imageUrl = imageUrl,
        acquired = acquired,
    )
