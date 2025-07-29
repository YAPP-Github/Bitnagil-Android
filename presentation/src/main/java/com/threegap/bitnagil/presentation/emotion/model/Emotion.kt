package com.threegap.bitnagil.presentation.emotion.model

import com.threegap.bitnagil.designsystem.R
import com.threegap.bitnagil.domain.emotion.model.Emotion as DomainEmotion

enum class Emotion(
    val emotionName: String,
    val imageResourceId: Int,
) {
    CALM(emotionName = "평온함", imageResourceId = R.drawable.calm),
    VITALITY(emotionName = "활기참", imageResourceId = R.drawable.vitality),
    LETHARGY(emotionName = "무기력함", imageResourceId = R.drawable.lethargy),
    ANXIETY(emotionName = "불안함", imageResourceId = R.drawable.anxiety),
    SATISFACTION(emotionName = "만족함", imageResourceId = R.drawable.satisfaction),
    FATIGUE(emotionName = "피로함", imageResourceId = R.drawable.fatigue),
    ;

    companion object {
        fun fromDomain(domain: DomainEmotion): Emotion {
            return when (domain) {
                DomainEmotion.CALM -> CALM
                DomainEmotion.VITALITY -> VITALITY
                DomainEmotion.LETHARGY -> LETHARGY
                DomainEmotion.ANXIETY -> ANXIETY
                DomainEmotion.SATISFACTION -> SATISFACTION
                DomainEmotion.FATIGUE -> FATIGUE
            }
        }
    }

    fun toDomain(): DomainEmotion {
        return when (this) {
            CALM -> DomainEmotion.CALM
            VITALITY -> DomainEmotion.VITALITY
            LETHARGY -> DomainEmotion.LETHARGY
            ANXIETY -> DomainEmotion.ANXIETY
            SATISFACTION -> DomainEmotion.SATISFACTION
            FATIGUE -> DomainEmotion.FATIGUE
        }
    }
}
