package com.threegap.bitnagil.presentation.screen.summary.model

import com.threegap.bitnagil.domain.activitylog.model.EmotionMarble
import com.threegap.bitnagil.domain.emotion.model.EmotionMarbleType
import java.time.LocalDate

data class SummaryEmotionCellUiModel(
    val date: LocalDate,
    val emotionType: SummaryEmotionType,
    val imageUrl: String,
)

fun EmotionMarble.toUiModel(): SummaryEmotionCellUiModel =
    SummaryEmotionCellUiModel(
        date = date,
        emotionType = type.toUiModel(),
        imageUrl = imageUrl,
    )

fun EmotionMarbleType.toUiModel(): SummaryEmotionType =
    when (this) {
        EmotionMarbleType.CALM -> SummaryEmotionType.CALM
        EmotionMarbleType.VITALITY -> SummaryEmotionType.VITALITY
        EmotionMarbleType.LETHARGY -> SummaryEmotionType.LETHARGY
        EmotionMarbleType.ANXIETY -> SummaryEmotionType.ANXIETY
        EmotionMarbleType.SATISFACTION -> SummaryEmotionType.SATISFACTION
        EmotionMarbleType.FATIGUE -> SummaryEmotionType.FATIGUE
    }
