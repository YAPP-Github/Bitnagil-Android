package com.threegap.bitnagil.presentation.onboarding.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstractTextItem as DomainOnBoardingAbstractTextItem

@Parcelize
data class OnBoardingAbstractTextItem(
    val text: String,
    val isBold: Boolean,
) : Parcelable {
    companion object {
        fun fromOnBoardingAbstractTextItem(onBoardingAbstractTextItem: DomainOnBoardingAbstractTextItem) : OnBoardingAbstractTextItem {
            return OnBoardingAbstractTextItem(
                text = onBoardingAbstractTextItem.text,
                isBold = onBoardingAbstractTextItem.isBold,
            )
        }
    }
}
