package com.threegap.bitnagil.presentation.onboarding.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnBoardingItem(
    val id: String,
    val title: String,
    val description: String?,
    val selected: Boolean,
) : Parcelable
