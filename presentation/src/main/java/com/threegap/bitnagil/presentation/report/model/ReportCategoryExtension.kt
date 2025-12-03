package com.threegap.bitnagil.presentation.report.model

import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.domain.report.model.ReportCategory

val ReportCategory.uiTitle: String
    get() = when (this) {
        ReportCategory.TRANSPORTATION -> "교통 시설"
        ReportCategory.LIGHTING -> "조명 시설"
        ReportCategory.WATERFACILITY -> "상하수도 시설"
        ReportCategory.AMENITY -> "편의 시설"
    }

val ReportCategory.uiDescription: String
    get() = when (this) {
        ReportCategory.TRANSPORTATION -> "신호등 고장, 표지판 파손, 횡단보도 등"
        ReportCategory.LIGHTING -> "가로등, 보안등 파손 등"
        ReportCategory.WATERFACILITY -> "맨홀 뚜껑 손상 등"
        ReportCategory.AMENITY -> "벤치 파손, 휴지통 넘침 등"
    }

val ReportCategory.iconRes: Int
    get() = when (this) {
        ReportCategory.TRANSPORTATION -> R.drawable.ic_car
        ReportCategory.LIGHTING -> R.drawable.ic_light
        ReportCategory.WATERFACILITY -> R.drawable.ic_water
        ReportCategory.AMENITY -> R.drawable.ic_hammer
    }
