package com.threegap.bitnagil.presentation.report.model

import androidx.annotation.DrawableRes
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.domain.report.model.ReportCategory

enum class ReportCategoryUi(
    @DrawableRes val icon: Int,
    val title: String,
    val description: String,
) {
    TRANSPORTATION(
        icon = R.drawable.ic_car,
        title = "교통 시설",
        description = "신호등 고장, 표지판 파손, 횡단보도 등",
    ),
    LIGHTING(
        icon = R.drawable.ic_light,
        title = "조명 시설",
        description = "가로등, 보안등 파손 등",
    ),
    WATERFACILITY(
        icon = R.drawable.ic_water,
        title = "상하수도 시설",
        description = "맨홀 뚜껑 손상 등",
    ),
    AMENITY(
        icon = R.drawable.ic_hammer,
        title = "편의 시설",
        description = "벤치 파손, 휴지통 넘침 등",
    ),
    ;

    fun toDomain(): ReportCategory {
        return when (this) {
            TRANSPORTATION -> ReportCategory.TRANSPORTATION
            LIGHTING -> ReportCategory.LIGHTING
            WATERFACILITY -> ReportCategory.WATERFACILITY
            AMENITY -> ReportCategory.AMENITY
        }
    }
}
