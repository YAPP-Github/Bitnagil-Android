package com.threegap.bitnagil.presentation.screen.summary.model

import androidx.compose.ui.graphics.Color

enum class SummaryEmotionType(
    val backgroundColor: Color,
    val textColor: Color,
    val displayName: String,
) {
    CALM(Color(0xFFEFECFF), Color(0xFF692BD0), "평온함"),
    VITALITY(Color(0xFFE9FAD0), Color(0xFF609F00), "활기참"),
    LETHARGY(Color(0xFFEAEBEC), Color(0xFF5A5C63), "무기력함"),
    ANXIETY(Color(0xFFFFEEE4), Color(0xFFFE7120), "불안함"),
    SATISFACTION(Color(0xFFE2F3F6), Color(0xFF26A792), "만족함"),
    FATIGUE(Color(0xFFFFE1E1), Color(0xFFFF5151), "피곤함")
}
