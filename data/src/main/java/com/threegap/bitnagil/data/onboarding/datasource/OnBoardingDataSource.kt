package com.threegap.bitnagil.data.onboarding.datasource

import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingAbstractDto
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingDto
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingRecommendRoutineDto
import com.threegap.bitnagil.data.onboarding.model.request.OnBoardingRecommendRoutinesRequest

interface OnBoardingDataSource {
    suspend fun getOnBoardingList(): List<OnBoardingDto>
    suspend fun getOnBoardingRecommendRoutines(request: OnBoardingRecommendRoutinesRequest): Result<List<OnBoardingRecommendRoutineDto>>
    suspend fun getOnBoardingAbstract(selectedOnBoardingItemIdList: List<Pair<String, List<String>>>): OnBoardingAbstractDto
}
