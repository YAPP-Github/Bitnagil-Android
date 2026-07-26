package com.threegap.bitnagil.presentation.screen.summary.model

import java.time.LocalDate

data class SummaryEmotionDayUiModel(
    val date: LocalDate,
    val emotionType: SummaryEmotionType,
    val imageUrl: String,
) {
    val dayText: String
        get() = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일의 감정"

    val emotionText: String
        get() = when(emotionType) {
            SummaryEmotionType.CALM -> "이날은 평온했나봐요! 평온함은 마음이 고요하고 편안해 균형을 이루는 상태예요."
            SummaryEmotionType.VITALITY -> "이날은 활기찼나봐요! 활기참은 생기가 가득 차 활발하고 적극적인 상태예요."
            SummaryEmotionType.LETHARGY -> "이날은 무기력했나봐요! 무기력함은 의욕이 없어 아무것도 하기 힘든 상태예요."
            SummaryEmotionType.ANXIETY -> "이날은 불안했나봐요! 불안함은 마음이 불안정하고 쉽게 안심하기 어려운 상태예요."
            SummaryEmotionType.SATISFACTION -> "이날은 만족스러웠나봐요! 만족함은 기대가 충족되어 더 바랄 것이 없는 상태예요."
            SummaryEmotionType.FATIGUE -> "이날은 피곤했나봐요! 피곤함은 몸과 마음이 지쳐 휴식이 필요한 상태예요."
        }
}
