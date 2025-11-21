package com.threegap.bitnagil.domain.file.usecase

import com.threegap.bitnagil.domain.file.model.ImageFile
import com.threegap.bitnagil.domain.file.repository.FileRepository
import javax.inject.Inject

class UploadReportImagesUseCase @Inject constructor(
    private val fileRepository: FileRepository,
) {
    suspend operator fun invoke(imageFiles: List<ImageFile>): Result<List<String>> {
        return fileRepository.uploadImages(imageFiles)
    }
}
