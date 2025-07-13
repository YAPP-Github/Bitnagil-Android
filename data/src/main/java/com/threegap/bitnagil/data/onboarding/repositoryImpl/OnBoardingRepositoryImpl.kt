package com.threegap.bitnagil.data.onboarding.repositoryImpl

import com.threegap.bitnagil.data.onboarding.datasource.OnBoardingDataSource
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingDto
import com.threegap.bitnagil.data.onboarding.model.request.GetOnBoardingRecommendRoutinesRequest
import com.threegap.bitnagil.data.onboarding.model.request.RegisterOnBoardingRecommendRoutinesRequest
import com.threegap.bitnagil.domain.onboarding.model.OnBoarding
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstract
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingRecommendRoutine
import com.threegap.bitnagil.domain.onboarding.repository.OnBoardingRepository
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val onBoardingDataSource: OnBoardingDataSource
) : OnBoardingRepository {
    override suspend fun getOnBoardingList(): List<OnBoarding> {
        val onBoardingDtoList = onBoardingDataSource.getOnBoardingList()
        return onBoardingDtoList.map { onBoardingDto ->
            onBoardingDto.toOnBoarding()
        }
    }

    override suspend fun getOnBoardingAbstract(selectedOnBoardingItemsList: List<Pair<String, List<String>>>): OnBoardingAbstract {
        val onBoardingAbstractDto = onBoardingDataSource.getOnBoardingAbstract(selectedOnBoardingItemsList)
        return onBoardingAbstractDto.toOnBoardingAbstract()
    }


    override suspend fun getRecommendOnBoardingRouteList(selectedOnBoardingItemsList: List<Pair<String, List<String>>>): Result<List<OnBoardingRecommendRoutine>> {
        val timeSlot = selectedOnBoardingItemsList.find { it.first == OnBoardingDto.TimeSlot.id }?.second?.first()
        val emotionType = selectedOnBoardingItemsList.find { it.first == OnBoardingDto.EmotionType.id }?.second?.first()
        val realOutingFrequency = selectedOnBoardingItemsList.find { it.first == OnBoardingDto.RealOutingFrequency.id }?.second?.first()
        val targetOutingFrequency = selectedOnBoardingItemsList.find { it.first == OnBoardingDto.TargetOutingFrequency.id }?.second?.first()

        val request = GetOnBoardingRecommendRoutinesRequest(
            timeSlot = timeSlot ?: "",
            emotionType = emotionType ?: "",
            realOutingFrequency = realOutingFrequency ?: "",
            targetOutingFrequency = targetOutingFrequency ?: ""
        )

        return onBoardingDataSource.getOnBoardingRecommendRoutines(request = request).map {
            it.recommendedRoutines.map { onBoardingRecommendRoutineDto ->
                onBoardingRecommendRoutineDto.toOnBoardingRecommendRoutine()
            }
        }
    }

    override suspend fun registerRecommendRoutineList(selectedRecommendRoutineIdList: List<String>): Result<Unit> {
        val request = RegisterOnBoardingRecommendRoutinesRequest(recommendedRoutineIds = selectedRecommendRoutineIdList)

        // todo - 서버측 구현시 연결
        return Result.success(Unit)
    }

}
