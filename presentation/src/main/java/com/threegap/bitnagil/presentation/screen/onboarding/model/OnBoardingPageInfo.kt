package com.threegap.bitnagil.presentation.screen.onboarding.model

import android.os.Parcelable
import androidx.compose.runtime.Stable
import com.threegap.bitnagil.domain.onboarding.model.OnBoarding
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OnBoardingPageInfo : Parcelable {
    @Parcelize
    data object Intro : OnBoardingPageInfo()

    @Parcelize
    data class ExistedOnBoardingAbstract(
        val prefix: String,
        @Stable val abstractTexts: List<List<OnBoardingAbstractTextItemUiModel>>,
    ) : OnBoardingPageInfo()

    @Parcelize
    data class SelectOnBoarding(
        val id: String,
        val title: String,
        val description: String?,
        @Stable val items: List<OnBoardingItemUiModel> = emptyList(),
        val multipleSelectable: Boolean = false,
    ) : OnBoardingPageInfo() {
        companion object {
            private var lastSelectedIndex = 0
        }

        val isItemSelected: Boolean get() = items.any { it.selectedIndex != null }

        fun selectItem(itemId: String): SelectOnBoarding {
            return copy(
                items = items.map {
                    if (it.id == itemId) {
                        val alreadySelected = (it.selectedIndex != null)
                        val selectedIndex = if (alreadySelected) {
                            null
                        } else {
                            lastSelectedIndex++
                        }
                        it.copy(selectedIndex = selectedIndex)
                    } else {
                        if (!multipleSelectable) {
                            it.copy(selectedIndex = null)
                        } else {
                            it
                        }
                    }
                },
            )
        }
    }

    @Parcelize
    data class Abstract(
        val prefix: String,
        @Stable val abstractTexts: List<List<OnBoardingAbstractTextItemUiModel>>,
    ) : OnBoardingPageInfo()

    @Parcelize
    data class RecommendRoutines(@Stable val routines: List<OnBoardingItemUiModel>) : OnBoardingPageInfo() {
        companion object {
            private var lastSelectedIndex = 0
        }

        val isItemSelected: Boolean get() = routines.any { it.selectedIndex != null }

        fun selectItem(itemId: String): RecommendRoutines {
            return copy(
                routines = routines.map {
                    if (it.id == itemId) {
                        val alreadySelected = (it.selectedIndex != null)
                        val selectedIndex = if (alreadySelected) {
                            null
                        } else {
                            lastSelectedIndex++
                        }
                        it.copy(selectedIndex = selectedIndex)
                    } else {
                        it
                    }
                },
            )
        }
    }
}

internal fun OnBoarding.toUiModel(): OnBoardingPageInfo.SelectOnBoarding =
    OnBoardingPageInfo.SelectOnBoarding(
        id = this.id,
        title = this.title,
        description = this.description,
        items = this.onboardingItemList.map { it.toUiModel() },
        multipleSelectable = this.multipleSelectable,
    )
