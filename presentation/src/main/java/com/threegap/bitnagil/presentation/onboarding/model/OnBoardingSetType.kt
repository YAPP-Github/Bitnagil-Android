package com.threegap.bitnagil.presentation.onboarding.model

import com.threegap.bitnagil.presentation.onboarding.model.navarg.OnBoardingScreenArg

enum class OnBoardingSetType(
    val subText: String,
    val canSkip: Boolean,
    val canSelectRoutine: Boolean,
) {
    NEW(
        subText = "당신의 생활 패턴과 목표에 맞춰 구성된 맞춤 루틴이에요.\n지금부터 가볍게 시작해보세요.",
        canSkip = true,
        canSelectRoutine = false,
    ),
    RESET(
        subText = "생활 패턴과 목표에 맞춰 다시 구성된 맞춤 루틴이에요.\n원하는 루틴을 선택해서 가볍게 시작해보세요.",
        canSkip = false,
        canSelectRoutine = true,
    ),
    ;

    companion object {
        fun fromOnBoardingScreenArg(
            onBoardingScreenArg: OnBoardingScreenArg,
        ): OnBoardingSetType {
            return when (onBoardingScreenArg) {
                OnBoardingScreenArg.NEW -> NEW
                OnBoardingScreenArg.RESET -> RESET
            }
        }
    }
}
