package com.threegap.bitnagil.presentation.screen.reporthistory.model

data class ReportStatusFilterWithCount(
    val filter: ReportStatusFilter,
    val count: Int,
) {
    val titleWithCount: String = if (count == 0) filter.getTitle() else "${filter.getTitle()} $count"

    companion object {
        val Init = ReportStatusFilterWithCount(
            filter = ReportStatusFilter.All,
            count = 0,
        )
    }
}
