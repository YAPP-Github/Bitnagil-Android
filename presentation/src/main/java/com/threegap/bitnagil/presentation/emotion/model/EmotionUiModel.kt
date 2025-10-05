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
    val message: String? = null,
    val symbolBackgroundColor: Long = 0xFFEAEBEC,
    val symbolColor: Long = 0xFF878A93,
) : Parcelable {
    companion object {
        fun fromDomain(emotion: Emotion) = EmotionUiModel(
            emotionType = emotion.emotionType,
            emotionMarbleName = emotion.emotionMarbleName,
            image = EmotionImageUiModel.Url(
                url = emotion.imageUrl,
                offlineBackupImageResourceId = getOfflineBackupImageResourceId(emotion.emotionType),
            ),
            message = getMessage(emotion.emotionType),
            symbolBackgroundColor = getSymbolBackgroundColor(emotion.emotionType),
            symbolColor = getSymbolColor(emotion.emotionType),
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

        private fun getMessage(emotionType: String): String? {
            return when (emotionType) {
                "CALM" -> "평온함은 마음이 고요하고 편안해\n균형을 이루는 상태에요."
                "VITALITY" -> "활기참은 생기가 가득 차\n활발하고 적극적인 상태예요."
                "LETHARGY" -> "무기력함은 의욕이 없어 아무것도\n하기 힘든 상태예요."
                "ANXIETY" -> "불안함은 마음이 불안정하고 쉽게\n안심하기 어려운 상태예요."
                "SATISFACTION" -> "만족함은 기대가 충족되어\n더 바랄 것이 없는 상태예요."
                "FATIGUE" -> "피곤함은 몸과 마음이 지쳐\n휴식이 필요한 상태예요."
                else -> null
            }
        }

        private fun getSymbolBackgroundColor(emotionType: String): Long {
            return when (emotionType) {
                "CALM" -> 0xFFEFECFF
                "VITALITY" -> 0xFFE9FAD0
                "LETHARGY" -> 0xFFEAEBEC
                "ANXIETY" -> 0xFFFFEEE4
                "SATISFACTION" -> 0xFFE2F3F6
                "FATIGUE" -> 0xFFFFE1E1
                else -> 0xFFEAEBEC
            }
        }

        private fun getSymbolColor(emotionType: String): Long {
            return when (emotionType) {
                "CALM" -> 0xFF692BD0
                "VITALITY" -> 0xFF609F00
                "LETHARGY" -> 0xFF5A5C63
                "ANXIETY" -> 0xFFFE7120
                "SATISFACTION" -> 0xFF26A792
                "FATIGUE" -> 0xFFFF5151
                else -> 0xFF878A93
            }
        }

        val Default = EmotionUiModel(
            emotionType = "NONE",
            emotionMarbleName = "구슬 선택",
            image = EmotionImageUiModel.Resource(R.drawable.default_marble),
            selectable = false,
            message = "오늘 기분 어때요?\n기록해 두면 내 루틴에 도움 돼요!",
            symbolBackgroundColor = 0xFFEAEBEC,
            symbolColor = 0xFF171719,
        )
    }
}
