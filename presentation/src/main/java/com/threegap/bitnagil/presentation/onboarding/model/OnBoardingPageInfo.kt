package com.threegap.bitnagil.presentation.onboarding.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.threegap.bitnagil.domain.onboarding.model.OnBoarding
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OnBoardingPageInfo : Parcelable {

    @Parcelize
    data class SelectOnBoarding(
        val title: String,
        val description: String?,
        @Stable val items: List<OnBoardingItem> = emptyList(),
    ) : OnBoardingPageInfo() {
        companion object {
            fun fromOnBoarding(onBoarding: OnBoarding) : SelectOnBoarding {
                return SelectOnBoarding(
                    title = onBoarding.title,
                    description = onBoarding.description,
                    items = onBoarding.onboardingItemList.map {
                        OnBoardingItem(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            selected = false,
                        )
                    }
                )
            }
        }
    }

    @Parcelize
    data class Abstract(val title: String, @Stable val selectedItems: List<OnBoardingItem>) : OnBoardingPageInfo()

    @Parcelize
    data class RecommendRoutines(@Stable val routineList: List<OnBoardingItem>) : OnBoardingPageInfo()


}
