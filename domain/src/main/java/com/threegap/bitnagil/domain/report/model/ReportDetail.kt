package com.threegap.bitnagil.domain.report.model

import java.time.LocalDate

data class ReportDetail(
    val id: String,
    val title: String,
    val content: String,
    val category: ReportCategory,
    val status: ReportStatus,
    val imageUrls: List<String>,
    val address: String,
    val date: LocalDate,
)
