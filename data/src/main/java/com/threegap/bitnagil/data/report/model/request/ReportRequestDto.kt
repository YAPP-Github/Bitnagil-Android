package com.threegap.bitnagil.data.report.model.request

import com.threegap.bitnagil.domain.report.model.Report
import com.threegap.bitnagil.domain.report.model.ReportCategory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportRequestDto(
    @SerialName("reportTitle")
    val reportTitle: String,
    @SerialName("reportContent")
    val reportContent: String,
    @SerialName("reportCategory")
    val reportCategory: ReportCategory,
    @SerialName("reportImageUrls")
    val reportImageUrls: List<String>,
    @SerialName("reportLocation")
    val reportLocation: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
)

fun Report.toDto(): ReportRequestDto {
    return ReportRequestDto(
        reportTitle = this.title,
        reportContent = this.content,
        reportCategory = this.category,
        reportImageUrls = this.imageUrls,
        reportLocation = this.address,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}
