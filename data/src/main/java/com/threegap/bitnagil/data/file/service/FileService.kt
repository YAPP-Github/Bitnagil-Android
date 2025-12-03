package com.threegap.bitnagil.data.file.service

import com.threegap.bitnagil.data.file.model.request.FileInfoRequestDto
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface FileService {
    @POST("/api/v2/files/presigned-urls")
    suspend fun fetchPresignedUrls(
        @Body fileInfos: List<FileInfoRequestDto>,
    ): BaseResponse<Map<String, String>>
}
