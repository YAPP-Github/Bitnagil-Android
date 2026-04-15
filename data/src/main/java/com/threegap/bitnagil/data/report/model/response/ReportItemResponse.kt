package com.threegap.bitnagil.data.report.model.response

import com.threegap.bitnagil.domain.report.model.ReportCategory
import com.threegap.bitnagil.domain.report.model.ReportItem
import com.threegap.bitnagil.domain.report.model.ReportStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportItemResponse(
    @SerialName("reportId")
    val reportId: Int,
    @SerialName("reportStatus")
    val reportStatus: ReportStatus,
    @SerialName("reportTitle")
    val reportTitle: String,
    @SerialName("reportCategory")
    val reportCategory: ReportCategory,
    @SerialName("reportLocation")
    val reportLocation: String,
    @SerialName("reportImageUrl")
    val reportImageUrl: String,
)

fun ReportItemResponse.toDomain(): ReportItem =
    ReportItem(
        id = this.reportId,
        status = this.reportStatus,
        title = this.reportTitle,
        category = this.reportCategory,
        address = this.reportLocation,
        imageUrl = this.reportImageUrl,
    )
