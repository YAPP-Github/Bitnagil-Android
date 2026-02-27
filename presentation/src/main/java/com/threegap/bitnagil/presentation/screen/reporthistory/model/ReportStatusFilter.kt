package com.threegap.bitnagil.presentation.screen.reporthistory.model

import com.threegap.bitnagil.domain.report.model.ReportStatus
import com.threegap.bitnagil.presentation.model.report.displayTitle

sealed class ReportStatusFilter {
    data object All : ReportStatusFilter()
    data class Specific(val status: ReportStatus) : ReportStatusFilter()

    fun getTitle(): String = when (this) {
        is All -> "전체"
        is Specific -> status.displayTitle
    }

    fun matches(status: ReportStatus): Boolean = when (this) {
        is All -> true
        is Specific -> this.status == status
    }

    companion object {
        fun values(): List<ReportStatusFilter> {
            return listOf(All) + ReportStatus.entries.map { Specific(it) }
        }
    }
}
