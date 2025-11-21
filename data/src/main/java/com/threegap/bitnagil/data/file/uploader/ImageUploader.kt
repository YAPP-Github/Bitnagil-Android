package com.threegap.bitnagil.data.file.uploader

import android.util.Log
import com.threegap.bitnagil.domain.file.model.ImageFile
import com.threegap.bitnagil.network.NoneAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import javax.inject.Inject

class ImageUploader @Inject constructor(
    @NoneAuth private val okHttpClient: OkHttpClient,
) {
    suspend fun uploadToPresignedUrl(imageFile: ImageFile, presignedUrl: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val requestBody = imageFile.bytes.toRequestBody(
                    contentType = imageFile.mimeType.toMediaTypeOrNull(),
                )

                val request = Request.Builder()
                    .url(presignedUrl)
                    .put(requestBody)
                    .build()

                okHttpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        val errorBody = response.body?.string() ?: "No error details"
                        Log.e("ImageUploader", "Upload failed: ${imageFile.name}, code=${response.code}, error=$errorBody")
                        throw IOException("Upload failed: ${response.code}")
                    }
                }
            }
        }
    }
}
