package com.threegap.bitnagil.data.file.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.file.datasource.FileDataSource
import com.threegap.bitnagil.data.file.model.request.FileInfoRequestDto
import com.threegap.bitnagil.data.file.service.FileService
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    private val fileService: FileService,
) : FileDataSource {
    override suspend fun fetchPresignedUrls(fileInfos: List<FileInfoRequestDto>): Result<Map<String, String>> {
        return safeApiCall { fileService.fetchPresignedUrls(fileInfos) }
    }
}
