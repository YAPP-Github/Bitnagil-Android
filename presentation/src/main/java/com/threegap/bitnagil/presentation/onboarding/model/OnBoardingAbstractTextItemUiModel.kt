package com.threegap.bitnagil.presentation.onboarding.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstractTextItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnBoardingAbstractTextItemUiModel(
    val text: String,
    val isBold: Boolean,
) : Parcelable

internal fun OnBoardingAbstractTextItem.toUiModel(): OnBoardingAbstractTextItemUiModel =
    OnBoardingAbstractTextItemUiModel(
        text = this.text,
        isBold = this.isBold,
    )
