package com.threegap.bitnagil.domain.report.model

enum class ReportCategory {
    TRANSPORTATION,
    LIGHTING,
    WATERFACILITY,
    AMENITY,
    ;

    companion object {
        fun fromString(value: String): ReportCategory {
            return when (value) {
                "TRANSPORTATION" -> TRANSPORTATION
                "LIGHTING" -> LIGHTING
                "WATERFACILITY" -> WATERFACILITY
                "AMENITY" -> AMENITY
                else -> throw IllegalArgumentException("Invalid ReportCategory value: $value")
            }
        }

        fun toString(value: ReportCategory): String {
            return when (value) {
                TRANSPORTATION -> "TRANSPORTATION"
                LIGHTING -> "LIGHTING"
                WATERFACILITY -> "WATERFACILITY"
                AMENITY -> "AMENITY"
            }
        }
    }
}
