package com.threegap.bitnagil.data.file.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileInfoRequestDto(
    @SerialName("prefix")
    val prefix: String,
    @SerialName("fileName")
    val fileName: String,
)
