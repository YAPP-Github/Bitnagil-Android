package com.threegap.bitnagil.presentation.reporthistory.model

data class ReportProcessWithCount(
    val process: ReportProcess,
    val count: Int,
) {
    val titleWithCount: String = if (count == 0) process.title else "${process.title} $count"

    companion object {
        val Init = ReportProcessWithCount(
            process = ReportProcess.Total,
            count = 0,
        )
    }
}
