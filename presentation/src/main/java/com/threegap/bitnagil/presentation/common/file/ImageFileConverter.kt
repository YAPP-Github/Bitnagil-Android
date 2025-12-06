package com.threegap.bitnagil.presentation.common.file

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.threegap.bitnagil.domain.file.model.ImageFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

fun extractFileName(uri: Uri, contentResolver: ContentResolver): String {
    contentResolver.query(
        uri,
        arrayOf(OpenableColumns.DISPLAY_NAME),
        null,
        null,
        null,
    )?.use { cursor ->
        if (cursor.moveToFirst()) {
            val name = cursor.getString(0)
            if (!name.isNullOrBlank()) return name
        }
    }

    val extension = MimeTypeMap.getSingleton()
        .getExtensionFromMimeType(contentResolver.getType(uri)) ?: "jpg"

    val uniqueId = UUID.randomUUID().toString().take(8)
    return "image_${System.currentTimeMillis()}_$uniqueId.$extension"
}

suspend fun convertUriToImageFile(
    uri: Uri,
    prefix: String,
    context: Context,
): ImageFile? = withContext(Dispatchers.IO) {
    try {
        val contentResolver = context.contentResolver
        val fileName = extractFileName(uri, contentResolver)
        val mimeType = contentResolver.getType(uri) ?: "image/jpeg"

        val bytes = contentResolver.openInputStream(uri)?.use { it.readBytes() }
            ?: return@withContext null

        ImageFile(
            prefix = prefix,
            name = fileName,
            mimeType = mimeType,
            bytes = bytes,
        )
    } catch (e: Exception) {
        null
    }
}

fun Context.createCameraImageUri(): Uri {
    val cameraDir = File(cacheDir, "camera").apply { if (!exists()) mkdirs() }
    val imageFile = File(cameraDir, "camera_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(this, "$packageName.provider", imageFile)
}
