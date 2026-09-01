package com.threegap.bitnagil.domain.activitylog.model

data class MonthlyBadge(
    val badgeTitle: String,
    val badgeDescription: String,
    val badges: List<Badge>,
)
