package com.threegap.bitnagil.presentation.reporthistory.model

data class ReportHistoryState(
    val reportProcesses: List<ReportProcess>,
    val reportCategories: List<ReportCategory>,
    val selectedReportCategory: ReportCategory?,
    val selectedReportProcess: ReportProcess,
    val reportHistoriesPerDays: List<ReportHistoriesPerDayUiModel>,
) {
    val reportProcessWithCounts: List<ReportProcessWithCount> = listOf(
        ReportProcessWithCount(ReportProcess.Total, reportHistoriesPerDays.sumOf { it.reports.size }),
        ReportProcessWithCount(
            ReportProcess.Reported,
            reportHistoriesPerDays.sumOf { it.reports.filter { report -> report.process == ReportProcess.Reported }.size },
        ),
        ReportProcessWithCount(
            ReportProcess.Progress,
            reportHistoriesPerDays.sumOf { it.reports.filter { report -> report.process == ReportProcess.Progress }.size },
        ),
        ReportProcessWithCount(
            ReportProcess.Complete,
            reportHistoriesPerDays.sumOf { it.reports.filter { report -> report.process == ReportProcess.Complete }.size },
        ),
    )

    companion object {
        val Init = ReportHistoryState(
            reportProcesses = listOf(),
            reportCategories = listOf(),
            selectedReportCategory = null,
            selectedReportProcess = ReportProcess.Total,
            reportHistoriesPerDays = listOf(),
        )
    }
}
