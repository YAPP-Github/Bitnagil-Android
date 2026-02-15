package com.threegap.bitnagil.data.file.repositoryImpl

import com.threegap.bitnagil.data.file.datasource.FileDataSource
import com.threegap.bitnagil.data.file.model.request.FileInfoRequest
import com.threegap.bitnagil.data.file.uploader.ImageUploader
import com.threegap.bitnagil.domain.file.model.ImageFile
import com.threegap.bitnagil.domain.file.repository.FileRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileDataSource: FileDataSource,
    private val imageUploader: ImageUploader,
) : FileRepository {
    override suspend fun uploadImages(imageFiles: List<ImageFile>): Result<List<String>> {
        return runCatching {
            val fileInfos = imageFiles.map { imageFile ->
                FileInfoRequest(prefix = imageFile.prefix, fileName = imageFile.name)
            }

            val presignedUrlMap: Map<String, String> = fileDataSource
                .fetchPresignedUrls(fileInfos)
                .getOrThrow()

            val presignedUrls = presignedUrlMap.values.toList()

            coroutineScope {
                imageFiles.zip(presignedUrls).map { (imageFile, presignedUrl) ->
                    async {
                        imageUploader
                            .uploadToPresignedUrl(imageFile, presignedUrl)
                            .getOrThrow()
                    }
                }.awaitAll()
            }

            presignedUrls.map { url -> url.substringBefore("?") }
        }
    }
}
