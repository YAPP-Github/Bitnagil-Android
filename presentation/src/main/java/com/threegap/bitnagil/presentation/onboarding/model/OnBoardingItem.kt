package com.threegap.bitnagil.presentation.onboarding.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingItem as DomainOnBoardingItem

@Parcelize
data class OnBoardingItem(
    val id: String,
    val title: String,
    val description: String?,
    val selected: Boolean,
) : Parcelable {
    companion object {
        fun fromOnBoardingItem(onBoardingItem: DomainOnBoardingItem) : OnBoardingItem {
            return OnBoardingItem(
                id = onBoardingItem.id,
                title = onBoardingItem.title,
                description = onBoardingItem.description,
                selected = false,
            )
        }
    }
}
