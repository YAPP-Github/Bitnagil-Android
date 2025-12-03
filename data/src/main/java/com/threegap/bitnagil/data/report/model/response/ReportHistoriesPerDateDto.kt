package com.threegap.bitnagil.data.report.model.response

import com.threegap.bitnagil.domain.report.model.ReportItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ReportHistoriesPerDateDto(
    @SerialName("reportInfos")
    val reportInfos: Map<String, List<ReportItemDto>>,
)

fun ReportHistoriesPerDateDto.toDomainMap(): Map<LocalDate, List<ReportItem>> =
    this.reportInfos
        .mapKeys { LocalDate.parse(it.key) }
        .mapValues { entry ->
            entry.value.map { it.toDomain() }
        }
