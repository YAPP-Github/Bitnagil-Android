package com.threegap.bitnagil.presentation.home.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.domain.emotion.model.Emotion

enum class EmotionBallType(
    @DrawableRes val drawableId: Int,
    val ambientColor: Color,
    val spotColor: Color,
) {
    CALM(
        drawableId = R.drawable.calm,
        ambientColor = Color(0xFFB987FF).copy(alpha = 0.57f),
        spotColor = Color(0xFFB987FF),
    ),
    VITALITY(
        R.drawable.vitality,
        ambientColor = Color(0xFF55840F).copy(alpha = 0.25f),
        spotColor = Color(0xFF55840F),
    ),
    LETHARGY(
        R.drawable.lethargy,
        ambientColor = Color(0xFF000000).copy(alpha = 0.19f),
        spotColor = Color(0xFF000000),
    ),
    ANXIETY(
        R.drawable.anxiety,
        ambientColor = Color(0xFFDE4C17).copy(alpha = 0.33f),
        spotColor = Color(0xFFDE4C17),
    ),
    SATISFACTION(
        R.drawable.satisfaction,
        ambientColor = Color(0xFF24846B).copy(alpha = 0.28f),
        spotColor = Color(0xFF24846B),
    ),
    FATIGUE(
        R.drawable.fatigue,
        ambientColor = Color(0xFFC71A1A).copy(alpha = 0.28f),
        spotColor = Color(0xFFC71A1A),
    ),
    ;

    companion object {
        fun fromDomainEmotion(emotion: Emotion?): EmotionBallType? =
            emotion?.let { valueOf(it.name) }
    }
}
