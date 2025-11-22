package com.threegap.bitnagil.data.report.service

import com.threegap.bitnagil.data.report.model.request.ReportRequestDto
import com.threegap.bitnagil.data.report.model.response.ReportHistoriesPerDateDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReportService {
    @POST("/api/v2/reports")
    suspend fun submitReport(
        @Body reportRequestDto: ReportRequestDto,
    ): BaseResponse<Long>

    @GET("/api/v2/reports")
    suspend fun getReports(): BaseResponse<ReportHistoriesPerDateDto>
}
