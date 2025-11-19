package com.threegap.bitnagil.presentation.reporthistory.model

data class ReportHistoryUiModel(
    val id: String,
    val title: String,
    val imageUrl: String,
    val location: String,
    val process: ReportProcess,
    val category: ReportCategory,
)
