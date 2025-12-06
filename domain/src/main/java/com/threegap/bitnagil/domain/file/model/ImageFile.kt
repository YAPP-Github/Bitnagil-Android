package com.threegap.bitnagil.domain.file.model

/**
 * 업로드할 이미지 파일을 나타내는 도메인 모델
 *
 * @property prefix S3 경로의 접두사 (예: "report", "profile")
 * @property name 파일명 (예: "image_1234567890.jpg")
 * @property mimeType MIME 타입 (예: "image/jpeg", "image/png")
 * @property bytes 이미지 파일의 바이트 데이터
 */
data class ImageFile(
    val prefix: String,
    val name: String,
    val mimeType: String,
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageFile

        if (prefix != other.prefix) return false
        if (name != other.name) return false
        if (mimeType != other.mimeType) return false
        if (!bytes.contentEquals(other.bytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = prefix.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + mimeType.hashCode()
        result = 31 * result + bytes.contentHashCode()
        return result
    }
}
