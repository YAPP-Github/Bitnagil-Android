package com.threegap.bitnagil.domain.emotion.model

import java.time.LocalDate

data class DailyEmotion(
    val type: EmotionMarbleType?,
    val name: String?,
    val imageUrl: String?,
    val homeMessage: String?,
    val fetchedDate: LocalDate = LocalDate.MIN,
) {
    val isStale: Boolean
        get() = fetchedDate != LocalDate.now()

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
