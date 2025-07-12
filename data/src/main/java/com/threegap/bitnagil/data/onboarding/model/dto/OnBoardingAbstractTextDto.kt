package com.threegap.bitnagil.data.onboarding.model.dto

import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstractText

data class OnBoardingAbstractTextDto(
    val textItemList: List<OnBoardingAbstractTextItemDto>
) {
    fun toOnBoardingAbstractText(): OnBoardingAbstractText {
        return OnBoardingAbstractText(
            textItemList = textItemList.map { textItemDto ->
                textItemDto.toOnBoardingAbstractTextItem()
            }
        )
    }
}
