package com.threegap.bitnagil.data.report.service

import com.threegap.bitnagil.data.report.model.request.ReportRequest
import com.threegap.bitnagil.data.report.model.response.ReportDetailResponse
import com.threegap.bitnagil.data.report.model.response.ReportHistoriesPerDateResponse
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReportService {
    @POST("/api/v2/reports")
    suspend fun submitReport(
        @Body reportRequest: ReportRequest,
    ): BaseResponse<Long>

    @GET("/api/v2/reports")
    suspend fun getReports(): BaseResponse<ReportHistoriesPerDateResponse>

    @GET("/api/v2/reports/{reportId}")
    suspend fun getReport(
        @Path("reportId") reportId: String,
    ): BaseResponse<ReportDetailResponse>
}
