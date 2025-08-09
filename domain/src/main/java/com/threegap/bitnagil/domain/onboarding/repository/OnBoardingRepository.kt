package com.threegap.bitnagil.domain.onboarding.repository

import com.threegap.bitnagil.domain.onboarding.model.OnBoarding
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstract
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingRecommendRoutine
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingRecommendRoutineEvent
import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {
    suspend fun getOnBoardingList(): List<OnBoarding>
    suspend fun getOnBoardingAbstract(selectedItemIdsWithOnBoardingId: List<Pair<String, List<String>>>): OnBoardingAbstract
    suspend fun getRecommendOnBoardingRouteList(selectedItemIdsWithOnBoardingId: List<Pair<String, List<String>>>): Result<List<OnBoardingRecommendRoutine>>
    suspend fun registerRecommendRoutineList(selectedRecommendRoutineIds: List<String>): Result<Unit>
    suspend fun getOnBoardingRecommendRoutineEventFlow(): Flow<OnBoardingRecommendRoutineEvent>
}
