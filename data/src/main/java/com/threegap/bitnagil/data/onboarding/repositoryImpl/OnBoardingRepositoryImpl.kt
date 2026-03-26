package com.threegap.bitnagil.data.onboarding.repositoryImpl

import com.threegap.bitnagil.data.onboarding.datasource.OnBoardingDataSource
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingDto
import com.threegap.bitnagil.data.onboarding.model.request.GetOnBoardingRecommendRoutinesRequest
import com.threegap.bitnagil.data.onboarding.model.request.RegisterOnBoardingRecommendRoutinesRequest
import com.threegap.bitnagil.data.routine.datasource.RoutineLocalDataSource
import com.threegap.bitnagil.domain.onboarding.model.OnBoarding
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstract
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingRecommendRoutine
import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val onBoardingDataSource: OnBoardingDataSource,
    private val routineLocalDataSource: RoutineLocalDataSource,
) : OnBoardingRepository {
    override suspend fun getOnBoardingList(): List<OnBoarding> {
        val onBoardingDtos = onBoardingDataSource.getOnBoardingList()
        return onBoardingDtos.map { onBoardingDto ->
            onBoardingDto.toOnBoarding()
        }
    }

    override suspend fun getOnBoardingAbstract(selectedItemIdsWithOnBoardingId: List<Pair<String, List<String>>>): OnBoardingAbstract {
        val onBoardingAbstractDto = onBoardingDataSource.getOnBoardingAbstract(selectedItemIdsWithOnBoardingId)
        return onBoardingAbstractDto.toOnBoardingAbstract()
    }

    override suspend fun getRecommendOnBoardingRouteList(
        selectedItemIdsWithOnBoardingId: List<Pair<String, List<String>>>,
    ): Result<List<OnBoardingRecommendRoutine>> {
        val timeSlot = selectedItemIdsWithOnBoardingId.find { it.first == OnBoardingDto.TimeSlot.id }?.second?.first()
        val emotionType = selectedItemIdsWithOnBoardingId.find { it.first == OnBoardingDto.EmotionType.id }?.second
        val realOutingFrequency = selectedItemIdsWithOnBoardingId.find { it.first == OnBoardingDto.RealOutingFrequency.id }?.second?.first()
        val targetOutingFrequency = selectedItemIdsWithOnBoardingId.find { it.first == OnBoardingDto.TargetOutingFrequency.id }?.second?.first()

        val request = GetOnBoardingRecommendRoutinesRequest(
            timeSlot = timeSlot ?: "",
            emotionType = emotionType ?: listOf(),
            realOutingFrequency = realOutingFrequency ?: "",
            targetOutingFrequency = targetOutingFrequency ?: "",
        )

        return onBoardingDataSource.getOnBoardingRecommendRoutines(request = request).map {
            it.recommendedRoutines.map { onBoardingRecommendRoutineDto ->
                onBoardingRecommendRoutineDto.toOnBoardingRecommendRoutine()
            }
        }
    }

    override suspend fun registerRecommendRoutineList(selectedRecommendRoutineIds: List<String>): Result<Unit> {
        val request = RegisterOnBoardingRecommendRoutinesRequest(
            recommendedRoutineIds = selectedRecommendRoutineIds.mapNotNull { it.toIntOrNull() },
        )

        return onBoardingDataSource.registerRecommendRoutineList(selectedRecommendRoutineIds = request.recommendedRoutineIds).also {
            if (it.isSuccess) {
                routineLocalDataSource.clearCache()
            }
        }
    }

    override suspend fun getUserOnBoarding(): Result<List<Pair<String, List<String>>>> {
        return onBoardingDataSource.getUserOnBoarding().map {
            listOf(
                OnBoardingDto.TimeSlot.id to listOf(it.timeSlot),
                OnBoardingDto.EmotionType.id to it.emotionTypes,
                OnBoardingDto.TargetOutingFrequency.id to listOf(it.targetOutingFrequency),
            )
        }
    }
}
