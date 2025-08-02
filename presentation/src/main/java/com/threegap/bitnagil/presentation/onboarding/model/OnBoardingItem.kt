package com.threegap.bitnagil.presentation.onboarding.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingRecommendRoutine
import kotlinx.parcelize.Parcelize
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingItem as DomainOnBoardingItem

@Parcelize
data class OnBoardingItem(
    val id: String,
    val title: String,
    val description: String?,
    val selectedIndex: Int? = null,
) : Parcelable {
    companion object {
        fun fromOnBoardingItem(onBoardingItem: DomainOnBoardingItem): OnBoardingItem {
            return OnBoardingItem(
                id = onBoardingItem.id,
                title = onBoardingItem.title,
                description = onBoardingItem.description,
                selectedIndex = null,
            )
        }

        fun fromOnBoardingRecommendRoutine(onBoardingRecommendRoutine: OnBoardingRecommendRoutine): OnBoardingItem {
            return OnBoardingItem(
                id = onBoardingRecommendRoutine.id,
                title = onBoardingRecommendRoutine.name,
                description = onBoardingRecommendRoutine.description,
                selectedIndex = null,
            )
        }
    }

    val selected: Boolean get() = selectedIndex != null
}
