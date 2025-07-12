package com.threegap.bitnagil.presentation.onboarding.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.threegap.bitnagil.domain.onboarding.model.OnBoarding
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OnBoardingPageInfo : Parcelable {

    @Parcelize
    data class SelectOnBoarding(
        val id: String,
        val title: String,
        val description: String?,
        @Stable val items: List<OnBoardingItem> = emptyList(),
        val multipleSelectable: Boolean = false,
    ) : OnBoardingPageInfo() {
        companion object {
            fun fromOnBoarding(onBoarding: OnBoarding) : SelectOnBoarding {
                return SelectOnBoarding(
                    id = onBoarding.id,
                    title = onBoarding.title,
                    description = onBoarding.description,
                    items = onBoarding.onboardingItemList.map {
                        OnBoardingItem.fromOnBoardingItem(it)
                    },
                    multipleSelectable = onBoarding.multipleSelectable,
                )
            }
        }

        val isItemSelected : Boolean get() = items.any { it.selected }

        fun selectItem(itemId: String) : SelectOnBoarding {
            return copy(
                items = items.map {
                    if (it.id == itemId) {
                        it.copy(selected = !it.selected)
                    } else {
                        if (!multipleSelectable) {
                            it.copy(selected = false)
                        } else {
                            it
                        }
                    }
                }
            )
        }
    }

    @Parcelize
    data class Abstract(
        val prefix: String,
        @Stable val abstractTextList: List<List<OnBoardingAbstractTextItem>>
    ) : OnBoardingPageInfo()

    @Parcelize
    data class RecommendRoutines(@Stable val routineList: List<OnBoardingItem>) : OnBoardingPageInfo() {
        val isItemSelected : Boolean get() = routineList.any { it.selected }

        fun selectItem(itemId: String) : RecommendRoutines {
            return copy(
                routineList = routineList.map {
                    if (it.id == itemId) {
                        it.copy(selected = !it.selected)
                    } else {
                        it
                    }
                }
            )
        }
    }

}
