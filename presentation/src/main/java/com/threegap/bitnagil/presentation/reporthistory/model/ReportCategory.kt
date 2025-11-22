package com.threegap.bitnagil.presentation.reporthistory.model

import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.domain.report.model.ReportCategory as DomainReportCategory

enum class ReportCategory(
    val title: String,
    val description: String,
    val iconResourceId: Int,
) {
    TrafficFacilities(
        title = "교통 시설",
        description = "신호등 고장, 표지판 파손, 횡단보도 등",
        iconResourceId = R.drawable.ic_car,
    ),
    LightingFacilities(
        title = "조명 시설",
        description = "가로등, 보안등 파손 등",
        iconResourceId = R.drawable.ic_light,
    ),
    WaterFacilities(
        title = "상하수도 시설",
        description = "맨홀 뚜껑 손상 등",
        iconResourceId = R.drawable.ic_water,
    ),
    Amenities(
        title = "편의 시설",
        description = "벤치 파손, 휴지통 넘침 등",
        iconResourceId = R.drawable.ic_hammer,
    ),
    ;

    companion object {
        fun fromDomain(domainReportCategory: DomainReportCategory): ReportCategory {
            return when (domainReportCategory) {
                DomainReportCategory.TRANSPORTATION -> TrafficFacilities
                DomainReportCategory.LIGHTING -> LightingFacilities
                DomainReportCategory.WATERFACILITY -> WaterFacilities
                DomainReportCategory.AMENITY -> Amenities
            }
        }
    }
}
