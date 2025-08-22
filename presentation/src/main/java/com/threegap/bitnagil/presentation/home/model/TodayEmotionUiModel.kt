package com.threegap.bitnagil.presentation.home.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.emotion.model.TodayEmotion
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodayEmotionUiModel(
    val imageUrl: String,
    val homeMessage: String,
) : Parcelable

fun TodayEmotion.toUiModel() = TodayEmotionUiModel(
    imageUrl = this.imageUrl,
    homeMessage = this.homeMessage,
)
