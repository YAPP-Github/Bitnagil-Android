package com.threegap.bitnagil.domain.emotion.model

import java.time.LocalDate

data class DailyEmotion(
    val type: EmotionMarbleType?,
    val name: String?,
    val imageUrl: String?,
    val homeMessage: String?,
    val fetchedDate: LocalDate = LocalDate.MIN,
) {
    fun isStale(today: LocalDate): Boolean = fetchedDate != today

    companion object {
        val INIT = DailyEmotion(
            type = null,
            name = null,
            imageUrl = null,
            homeMessage = null,
            fetchedDate = LocalDate.MIN,
        )
    }
}
