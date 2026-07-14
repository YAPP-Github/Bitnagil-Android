package com.threegap.bitnagil.domain.activitylog.model

import com.threegap.bitnagil.domain.emotion.model.EmotionMarbleType
import java.time.LocalDate

data class EmotionMarble(
    val date: LocalDate,
    val type: EmotionMarbleType,
    val name: String,
    val imageUrl: String,
)
