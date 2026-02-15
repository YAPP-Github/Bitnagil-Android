package com.threegap.bitnagil.presentation.onboarding.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstractTextItem

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
