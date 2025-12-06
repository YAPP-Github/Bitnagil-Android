package com.threegap.bitnagil.data.file.datasource

import com.threegap.bitnagil.data.file.model.request.FileInfoRequestDto

interface FileDataSource {
    /**
     * presigned URL 요청
     * @param fileInfos 파일 정보 리스트 (prefix + fileName)
     * @return key: S3 경로, value: presigned URL
     */
    suspend fun fetchPresignedUrls(fileInfos: List<FileInfoRequestDto>): Result<Map<String, String>>
}
