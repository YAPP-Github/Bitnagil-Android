package com.threegap.bitnagil.data.report.model.response

import com.threegap.bitnagil.domain.report.model.ReportItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ReportHistoriesPerDateResponse(
    @SerialName("reportInfos")
    val reportInfos: Map<String, List<ReportItemResponse>>,
)

fun ReportHistoriesPerDateResponse.toDomainMap(): Map<LocalDate, List<ReportItem>> =
    this.reportInfos
        .mapKeys { LocalDate.parse(it.key) }
        .mapValues { entry ->
            entry.value.map { it.toDomain() }
        }
