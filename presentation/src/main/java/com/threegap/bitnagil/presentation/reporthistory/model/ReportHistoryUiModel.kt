package com.threegap.bitnagil.presentation.reporthistory.model

import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.domain.report.model.ReportItem
import com.threegap.bitnagil.domain.report.model.ReportStatus

data class ReportHistoryUiModel(
    val id: String,
    val title: String,
    val imageUrl: String,
    val location: String,
    val status: ReportStatus,
    val category: ReportCategory,
)

internal fun ReportItem.toUiModel(): ReportHistoryUiModel =
    ReportHistoryUiModel(
        id = this.id.toString(),
        title = this.title,
        imageUrl = this.imageUrl,
        location = this.address,
        status = this.status,
        category = this.category,
    )
