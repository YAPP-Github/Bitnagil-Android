package com.threegap.bitnagil.presentation.screen.summary.model

sealed interface BadgeImage {
    data class Remote(val url: String) : BadgeImage
    data object Default : BadgeImage
}
