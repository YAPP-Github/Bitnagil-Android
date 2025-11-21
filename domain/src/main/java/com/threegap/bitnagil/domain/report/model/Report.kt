package com.threegap.bitnagil.domain.report.model

data class Report(
    val title: String,
    val content: String,
    val category: ReportCategory,
    val imageUrls: List<String>,
    val address: String,
    val latitude: Double,
    val longitude: Double,
)
