package com.threegap.bitnagil.domain.file.repository

import com.threegap.bitnagil.domain.file.model.ImageFile

interface FileRepository {
    suspend fun uploadImages(imageFiles: List<ImageFile>): Result<List<String>>
}
