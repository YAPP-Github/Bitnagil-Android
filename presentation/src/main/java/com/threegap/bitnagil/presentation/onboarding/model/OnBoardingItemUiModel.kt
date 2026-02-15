package com.threegap.bitnagil.presentation.onboarding.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingItem
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingRecommendRoutine
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnBoardingItemUiModel(
    val id: String,
    val title: String,
    val description: String?,
    val selectedIndex: Int? = null,
) : Parcelable {
    val selected: Boolean
        get() = selectedIndex != null
}

internal fun OnBoardingItem.toUiModel(): OnBoardingItemUiModel =
    OnBoardingItemUiModel(
        id = this.id,
        title = this.title,
        description = this.description,
        selectedIndex = null,
    )

internal fun OnBoardingRecommendRoutine.toUiModel(): OnBoardingItemUiModel =
    OnBoardingItemUiModel(
        id = this.id,
        title = this.name,
        description = this.description,
        selectedIndex = null,
    )
