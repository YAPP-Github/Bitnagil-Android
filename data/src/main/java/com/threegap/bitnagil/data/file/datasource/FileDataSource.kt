package com.threegap.bitnagil.data.file.datasource

import com.threegap.bitnagil.data.file.model.request.FileInfoRequest

interface FileDataSource {
    /**
     * presigned URL 요청
     * @param fileInfos 파일 정보 리스트 (prefix + fileName)
     * @return key: S3 경로, value: presigned URL
     */
    suspend fun fetchPresignedUrls(fileInfos: List<FileInfoRequest>): Result<Map<String, String>>
}
