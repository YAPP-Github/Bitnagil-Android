package com.threegap.bitnagil.data.report.model.response

import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.domain.report.model.ReportDetail
import com.threegap.bitnagil.domain.report.model.ReportStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
class ReportDetailResponse(
    @SerialName("reportDate")
    val reportDate: String,
    @SerialName("reportStatus")
    val reportStatus: ReportStatus,
    @SerialName("reportTitle")
    val reportTitle: String,
    @SerialName("reportContent")
    val reportContent: String,
    @SerialName("reportCategory")
    val reportCategory: ReportCategory,
    @SerialName("reportLocation")
    val reportLocation: String,
    @SerialName("reportImageUrls")
    val reportImageUrls: List<String>,
)

fun ReportDetailResponse.toDomain(id: String?): ReportDetail =
    ReportDetail(
        id = id ?: "",
        date = LocalDate.parse(this.reportDate),
        status = this.reportStatus,
        title = this.reportTitle,
        content = this.reportContent,
        category = this.reportCategory,
        address = this.reportLocation,
        imageUrls = this.reportImageUrls,
    )
