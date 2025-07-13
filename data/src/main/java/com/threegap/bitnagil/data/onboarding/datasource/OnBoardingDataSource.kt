package com.threegap.bitnagil.data.onboarding.datasource

import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingAbstractDto
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingDto
import com.threegap.bitnagil.data.onboarding.model.request.OnBoardingRecommendRoutinesRequest
import com.threegap.bitnagil.data.onboarding.model.response.OnBoardingRecommendRoutinesResponse

interface OnBoardingDataSource {
    suspend fun getOnBoardingList(): List<OnBoardingDto>
    suspend fun getOnBoardingRecommendRoutines(request: OnBoardingRecommendRoutinesRequest): Result<OnBoardingRecommendRoutinesResponse>
    suspend fun getOnBoardingAbstract(selectedOnBoardingItemIdList: List<Pair<String, List<String>>>): OnBoardingAbstractDto
}
