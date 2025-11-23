package com.threegap.bitnagil.data.report.model.response

import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.domain.report.model.ReportDetail
import com.threegap.bitnagil.domain.report.model.ReportStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
class ReportDetailDto(
    @SerialName("reportDate")
    val reportDate: String,
    @SerialName("reportStatus")
    val reportStatus: String,
    @SerialName("reportTitle")
    val reportTitle: String,
    @SerialName("reportContent")
    val reportContent: String,
    @SerialName("reportCategory")
    val reportCategory: String,
    @SerialName("reportLocation")
    val reportLocation: String,
    @SerialName("reportImageUrls")
    val reportImageUrls: List<String>,
)

fun ReportDetailDto.toDomain(id: String?): ReportDetail =
    ReportDetail(
        id = id ?: "",
        date = LocalDate.parse(this.reportDate),
        status = ReportStatus.fromString(this.reportStatus),
        title = this.reportTitle,
        content = this.reportContent,
        category = ReportCategory.fromString(this.reportCategory),
        address = this.reportLocation,
        imageUrls = this.reportImageUrls,
    )
