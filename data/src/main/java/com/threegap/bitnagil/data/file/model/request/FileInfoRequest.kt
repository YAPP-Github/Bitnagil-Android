package com.threegap.bitnagil.data.file.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileInfoRequest(
    @SerialName("prefix")
    val prefix: String,
    @SerialName("fileName")
    val fileName: String,
)
