package com.threegap.bitnagil.presentation.reporthistory.model

import com.threegap.bitnagil.domain.report.model.ReportItem

data class ReportHistoryUiModel(
    val id: String,
    val title: String,
    val imageUrl: String,
    val location: String,
    val process: ReportProcess,
    val category: ReportCategory,
) {
    companion object {
        fun fromDomain(reportItem: ReportItem): ReportHistoryUiModel {
            return ReportHistoryUiModel(
                id = "${reportItem.id}",
                title = reportItem.title,
                imageUrl = reportItem.imageUrl,
                location = reportItem.address,
                process = ReportProcess.fromDomain(reportItem.status),
                category = ReportCategory.fromDomain(reportItem.category),
            )
        }
    }
}
