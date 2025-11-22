package com.threegap.bitnagil.data.report.model.response

import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.domain.report.model.ReportItem
import com.threegap.bitnagil.domain.report.model.ReportStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportItemDto(
    @SerialName("reportId")
    val reportId: Int,
    @SerialName("reportStatus")
    val reportStatus: String,
    @SerialName("reportTitle")
    val reportTitle: String,
    @SerialName("reportCategory")
    val reportCategory: String,
    @SerialName("reportLocation")
    val reportLocation: String,
    @SerialName("reportImageUrl")
    val reportImageUrl: String,
)

fun ReportItemDto.toDomain(): ReportItem =
    ReportItem(
        id = this.reportId,
        status = ReportStatus.fromString(this.reportStatus),
        title = this.reportTitle,
        category = ReportCategory.fromString(this.reportCategory),
        address = this.reportLocation,
        imageUrl = this.reportImageUrl,
    )
