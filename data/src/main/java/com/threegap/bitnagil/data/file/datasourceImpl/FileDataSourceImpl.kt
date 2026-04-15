package com.threegap.bitnagil.data.file.datasourceImpl

import com.threegap.bitnagil.data.file.datasource.FileDataSource
import com.threegap.bitnagil.data.file.model.request.FileInfoRequest
import com.threegap.bitnagil.data.file.service.FileService
import javax.inject.Inject

class FileDataSourceImpl @Inject constructor(
    private val fileService: FileService,
) : FileDataSource {
    override suspend fun fetchPresignedUrls(fileInfos: List<FileInfoRequest>): Result<Map<String, String>> =
        fileService.fetchPresignedUrls(fileInfos)
}
