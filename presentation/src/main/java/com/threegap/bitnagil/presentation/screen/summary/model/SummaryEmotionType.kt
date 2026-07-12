package com.threegap.bitnagil.presentation.screen.summary.model

import androidx.compose.ui.graphics.Color

enum class SummaryEmotionType(
    val backgroundColor: Color,
    val textColor: Color
) {
    CALM(Color(0xFFEFECFF), Color(0xFF692BD0)),
    VITALITY(Color(0xFFE9FAD0), Color(0xFF609F00)),
    LETHARGY(Color(0xFFEAEBEC), Color(0xFF5A5C63)),
    ANXIETY(Color(0xFFFFEEE4), Color(0xFFFE7120)),
    SATISFACTION(Color(0xFFE2F3F6), Color(0xFF26A792)),
    FATIGUE(Color(0xFFFFE1E1), Color(0xFFFF5151))
}
