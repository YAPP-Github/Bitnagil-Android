package com.threegap.bitnagil.presentation.reporthistory.model

data class ReportHistoryState(
    val selectedReportCategory: ReportCategory?,
    val selectedReportProcess: ReportProcess,
    val reportHistoriesPerDays: List<ReportHistoriesPerDayUiModel>,
    val showSelectReportCategoryBottomSheet: Boolean,
) {
    val filteredReportHistoriesPerDays: List<ReportHistoriesPerDayUiModel> = reportHistoriesPerDays
        .map { reportHistoriesPerDay ->
            reportHistoriesPerDay.copy(
                reports = reportHistoriesPerDay.reports.filter {
                    val processMatched = when (selectedReportProcess) {
                        ReportProcess.Total -> true
                        ReportProcess.Reported -> it.process == ReportProcess.Reported
                        ReportProcess.Progress -> it.process == ReportProcess.Progress
                        ReportProcess.Complete -> it.process == ReportProcess.Complete
                    }

                    val categoryMatched = when(selectedReportCategory) {
                        ReportCategory.TrafficFacilities -> it.category == ReportCategory.TrafficFacilities
                        ReportCategory.LightingFacilities -> it.category == ReportCategory.LightingFacilities
                        ReportCategory.WaterFacilities -> it.category == ReportCategory.WaterFacilities
                        ReportCategory.Amenities -> it.category == ReportCategory.Amenities
                        null -> true
                    }

                    processMatched && categoryMatched
                },
            )
        }
        .filter { reportHistoriesPerDay ->
            reportHistoriesPerDay.reports.isNotEmpty()
        }

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

    val showCategorySelectButton: Boolean = reportHistoriesPerDays.isNotEmpty()

    companion object {
        val Init = ReportHistoryState(
            selectedReportCategory = null,
            selectedReportProcess = ReportProcess.Total,
            reportHistoriesPerDays = listOf(),
            showSelectReportCategoryBottomSheet = false
        )
    }
}
