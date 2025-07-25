package com.threegap.bitnagil.presentation.emotion.model

import com.threegap.bitnagil.domain.emotion.model.Emotion as DomainEmotion

enum class Emotion(
    val emotionName: String,
    val imageResourceId: Int,
) {
    // todo - 이미지 리소스 전달받은 후 해당 이미지로 수정
    CALM(emotionName = "평온함", imageResourceId = android.R.drawable.ic_menu_report_image),
    VITALITY(emotionName = "활기참", imageResourceId = android.R.drawable.ic_menu_report_image),
    LETHARGY(emotionName = "무기력함", imageResourceId = android.R.drawable.ic_menu_report_image),
    ANXIETY(emotionName = "불안함", imageResourceId = android.R.drawable.ic_menu_report_image),
    SATISFACTION(emotionName = "만족함", imageResourceId = android.R.drawable.ic_menu_report_image),
    FATIGUE(emotionName = "피로함", imageResourceId = android.R.drawable.ic_menu_report_image),
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
