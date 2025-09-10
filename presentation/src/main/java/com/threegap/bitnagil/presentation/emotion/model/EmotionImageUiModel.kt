package com.threegap.bitnagil.presentation.emotion.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class EmotionImageUiModel : Parcelable {
    data class Url(val url: String, val offlineBackupImageResourceId: Int?) : EmotionImageUiModel()
    data class Resource(val resourceId: Int) : EmotionImageUiModel()
}
