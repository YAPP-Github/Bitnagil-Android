package com.threegap.bitnagil.presentation.screen.reportdetail.contract

import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.domain.report.model.ReportStatus
import java.time.LocalDate

data class ReportDetailState(
    val reportProcess: ReportStatus,
    val reportTitle: String,
    val reportContent: String,
    val reportCategory: ReportCategory,
    val imageUrls: List<String>,
    val location: String,
    val date: LocalDate,
) {
    companion object {
        val Init = ReportDetailState(
            reportProcess = ReportStatus.PENDING,
            reportTitle = "",
            reportContent = "",
            reportCategory = ReportCategory.TRANSPORTATION,
            imageUrls = emptyList(),
            location = "",
            date = LocalDate.now(),
        )
    }
}
