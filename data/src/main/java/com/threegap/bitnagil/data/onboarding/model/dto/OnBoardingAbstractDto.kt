package com.threegap.bitnagil.data.onboarding.model.dto

import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstract

data class OnBoardingAbstractDto(
    val prefixText: String,
    val detailTextsList: List<OnBoardingAbstractTextDto>
) {
    fun toOnBoardingAbstract(): OnBoardingAbstract {
        return OnBoardingAbstract(
            prefix = prefixText,
            abstractTexts = detailTextsList.map { detailTexts ->
                detailTexts.toOnBoardingAbstractText()
            }
        )
    }
}
