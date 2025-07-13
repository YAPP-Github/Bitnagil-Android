package com.threegap.bitnagil.data.onboarding.model.dto

import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstractTextItem

data class OnBoardingAbstractTextItemDto(
    val text: String,
    val isBold: Boolean,
) {
    fun toOnBoardingAbstractTextItem(): OnBoardingAbstractTextItem {
        return OnBoardingAbstractTextItem(
            text = text,
            isBold = isBold,
        )
    }
}
