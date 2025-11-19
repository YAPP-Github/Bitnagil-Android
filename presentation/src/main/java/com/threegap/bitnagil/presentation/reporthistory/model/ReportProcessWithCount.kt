package com.threegap.bitnagil.presentation.reporthistory.model

data class ReportProcessWithCount(
    val process: ReportProcess,
    val count: Int,
) {
    companion object {
        val Init = ReportProcessWithCount(
            process = ReportProcess.Total,
            count = 0,
        )
    }
}
