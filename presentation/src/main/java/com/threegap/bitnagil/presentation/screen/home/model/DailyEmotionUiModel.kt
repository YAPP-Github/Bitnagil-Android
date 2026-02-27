package com.threegap.bitnagil.presentation.screen.home.model

import com.threegap.bitnagil.domain.emotion.model.DailyEmotion
import com.threegap.bitnagil.domain.emotion.model.EmotionMarbleType

data class DailyEmotionUiModel(
    val type: EmotionMarbleType?,
    val imageUrl: String,
    val homeMessage: String,
) {
    val hasEmotion: Boolean
        get() = type != null

    companion object {
        val INIT = DailyEmotionUiModel(
            type = null,
            imageUrl = "",
            homeMessage = "",
        )
    }
}

internal fun DailyEmotion.toUiModel() =
    DailyEmotionUiModel(
        type = this.type,
        imageUrl = this.imageUrl ?: "",
        homeMessage = this.homeMessage?.let { "님,\n$it" } ?: "님, 오셨군요!\n오늘 기분은 어떤가요?",
    )
