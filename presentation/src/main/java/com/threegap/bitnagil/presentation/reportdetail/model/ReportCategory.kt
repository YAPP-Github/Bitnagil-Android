package com.threegap.bitnagil.presentation.reportdetail.model
import com.threegap.bitnagil.domain.report.model.ReportCategory as DomainReportCategory

enum class ReportCategory(
    val title: String,
) {
    TrafficFacilities(
        title = "교통 시설",
    ),
    LightingFacilities(
        title = "조명 시설",
    ),
    WaterFacilities(
        title = "상하수도 시설",
    ),
    Amenities(
        title = "편의 시설",
    ),
    ;

    companion object {
        fun fromDomain(domainReportCategory: com.threegap.bitnagil.domain.report.model.ReportCategory): ReportCategory {
            return when (domainReportCategory) {
                DomainReportCategory.TRANSPORTATION -> TrafficFacilities
                DomainReportCategory.LIGHTING -> LightingFacilities
                DomainReportCategory.WATERFACILITY -> WaterFacilities
                DomainReportCategory.AMENITY -> Amenities
            }
        }
    }
}
