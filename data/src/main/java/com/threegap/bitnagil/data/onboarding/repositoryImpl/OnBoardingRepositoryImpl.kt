package com.threegap.bitnagil.data.onboarding.repositoryImpl

import com.threegap.bitnagil.data.onboarding.datasource.OnBoardingDataSource
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


    override suspend fun getRecommendOnBoardingRouteList(): Result<List<OnBoardingRecommendRoutine>> {
        TODO("Not yet implemented")
    }

}
