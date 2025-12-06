package com.threegap.bitnagil.domain.report.model

data class ReportItem(
    val id: Int,
    val title: String,
    val category: ReportCategory,
    val status: ReportStatus,
    val imageUrl: String,
    val address: String,
)
