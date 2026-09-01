package com.threegap.bitnagil.datastore.routine.model

import kotlinx.serialization.Serializable

@Serializable
data class RoutineAchievement(
    val firstCompletionLogged: Boolean = false,
)
