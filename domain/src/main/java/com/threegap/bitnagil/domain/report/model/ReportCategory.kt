package com.threegap.bitnagil.domain.report.model

import kotlinx.serialization.Serializable

@Serializable
enum class ReportCategory {
    TRANSPORTATION,
    LIGHTING,
    WATERFACILITY,
    AMENITY,
    ;
}
