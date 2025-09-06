package com.threegap.bitnagil.presentation.emotion.model

import android.os.Parcelable
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.domain.emotion.model.Emotion
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmotionUiModel(
    val emotionType: String,
    val emotionMarbleName: String,
    val image: EmotionImageUiModel,
    val selectable: Boolean = true,
) : Parcelable {
    companion object {
        fun fromDomain(emotion: Emotion) = EmotionUiModel(
            emotionType = emotion.emotionType,
            emotionMarbleName = emotion.emotionMarbleName,
            image = EmotionImageUiModel.Url(
                url = emotion.imageUrl,
                offlineBackupImageResourceId = getOfflineBackupImageResourceId(emotion.emotionType)
            ),
        )

        private fun getOfflineBackupImageResourceId(emotionType: String): Int? {
            return when (emotionType) {
                "CALM" -> R.drawable.calm
                "VITALITY" -> R.drawable.vitality
                "LETHARGY" -> R.drawable.lethargy
                "ANXIETY" -> R.drawable.anxiety
                "SATISFACTION" -> R.drawable.satisfaction
                "FATIGUE" -> R.drawable.fatigue
                else -> null
            }
        }

        val Default = EmotionUiModel(
            emotionType = "NONE",
            emotionMarbleName = "구슬 선택",
            image = EmotionImageUiModel.Resource(R.drawable.default_marble),
            selectable = false,
        )
    }
}
