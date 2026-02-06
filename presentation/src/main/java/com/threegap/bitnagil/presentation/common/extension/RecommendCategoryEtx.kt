package com.threegap.bitnagil.presentation.common.extension

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory

val RecommendCategory.displayColor: Color
    @Composable get() = when (this) {
        RecommendCategory.OUTING -> BitnagilTheme.colors.skyBlue10
        RecommendCategory.WAKE_UP -> BitnagilTheme.colors.orange25
        RecommendCategory.CONNECT -> BitnagilTheme.colors.purple10
        RecommendCategory.REST -> BitnagilTheme.colors.green10
        RecommendCategory.GROW -> BitnagilTheme.colors.pink10
        RecommendCategory.PERSONALIZED -> BitnagilTheme.colors.yellow10
        RecommendCategory.OUTING_REPORT -> BitnagilTheme.colors.yellow10
        RecommendCategory.UNKNOWN -> BitnagilTheme.colors.yellow10
    }

val RecommendCategory.displayIcon: Int
    @DrawableRes get() = when (this) {
        RecommendCategory.OUTING -> R.drawable.ic_outside
        RecommendCategory.WAKE_UP -> R.drawable.ic_wakeup
        RecommendCategory.CONNECT -> R.drawable.ic_connect
        RecommendCategory.REST -> R.drawable.ic_rest
        RecommendCategory.GROW -> R.drawable.ic_grow
        RecommendCategory.PERSONALIZED -> R.drawable.ic_shine
        RecommendCategory.OUTING_REPORT -> R.drawable.ic_shine
        RecommendCategory.UNKNOWN -> R.drawable.ic_shine
    }

val RecommendCategory.displayTitle: String
    get() = when (this) {
        RecommendCategory.PERSONALIZED -> "맞춤 추천"
        RecommendCategory.OUTING -> "나가봐요"
        RecommendCategory.WAKE_UP -> "일어나요"
        RecommendCategory.CONNECT -> "연결해요"
        RecommendCategory.REST -> "쉬어가요"
        RecommendCategory.GROW -> "성장해요"
        RecommendCategory.OUTING_REPORT -> "신고"
        RecommendCategory.UNKNOWN -> "알 수 없음"
    }

val RecommendCategory.isVisible: Boolean
    get() = when (this) {
        RecommendCategory.OUTING_REPORT -> false
        RecommendCategory.UNKNOWN -> false
        else -> true
    }
