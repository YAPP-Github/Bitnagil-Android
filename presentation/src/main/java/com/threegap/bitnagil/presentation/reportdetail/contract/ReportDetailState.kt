package com.threegap.bitnagil.presentation.reportdetail.contract

import com.threegap.bitnagil.presentation.reportdetail.model.ReportCategory
import com.threegap.bitnagil.presentation.reportdetail.model.ReportProcess
import java.time.LocalDate

data class ReportDetailState(
    val reportProcess: ReportProcess,
    val reportTitle: String,
    val reportContent: String,
    val reportCategory: ReportCategory,
    val imageUrls: List<String>,
    val location: String,
    val date: LocalDate,
) {
    companion object {
        val Init = ReportDetailState(
            reportProcess = ReportProcess.Reported,
            reportTitle = "",
            reportContent = "",
            reportCategory = ReportCategory.TrafficFacilities,
            imageUrls = emptyList(),
            location = "",
            date = LocalDate.now(),
        )
    }
}
