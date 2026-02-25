package com.threegap.bitnagil.presentation.screen.reporthistory.contract

import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.presentation.screen.reporthistory.model.ReportHistoriesPerDayUiModel
import com.threegap.bitnagil.presentation.screen.reporthistory.model.ReportStatusFilter
import com.threegap.bitnagil.presentation.screen.reporthistory.model.ReportStatusFilterWithCount

data class ReportHistoryState(
    val selectedReportCategory: ReportCategory?,
    val selectedReportStatusFilter: ReportStatusFilter,
    val reportHistoriesPerDays: List<ReportHistoriesPerDayUiModel>,
    val showSelectReportCategoryBottomSheet: Boolean,
) {
    val filteredReportHistoriesPerDays: List<ReportHistoriesPerDayUiModel> =
        reportHistoriesPerDays
            .map { reportHistoriesPerDay ->
                reportHistoriesPerDay.copy(
                    reports = reportHistoriesPerDay.reports.filter { report ->
                        val statusMatched = selectedReportStatusFilter.matches(report.status)

                        val categoryMatched = when (selectedReportCategory) {
                            null -> true
                            else -> report.category == selectedReportCategory
                        }

                        statusMatched && categoryMatched
                    },
                )
            }
            .filter { reportHistoriesPerDay ->
                reportHistoriesPerDay.reports.isNotEmpty()
            }

    val reportStatusFilterWithCounts: List<ReportStatusFilterWithCount> =
        ReportStatusFilter.values().map { filter ->
            val count = reportHistoriesPerDays.sumOf { day ->
                day.reports.count { report ->
                    filter.matches(report.status)
                }
            }
            ReportStatusFilterWithCount(filter, count)
        }

    val showCategorySelectButton: Boolean = reportHistoriesPerDays.isNotEmpty()

    companion object {
        val Init = ReportHistoryState(
            selectedReportCategory = null,
            selectedReportStatusFilter = ReportStatusFilter.All,
            reportHistoriesPerDays = listOf(),
            showSelectReportCategoryBottomSheet = false,
        )
    }
}
