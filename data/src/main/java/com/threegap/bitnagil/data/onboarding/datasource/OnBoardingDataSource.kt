package com.threegap.bitnagil.data.onboarding.datasource

import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingAbstractDto
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingDto
import com.threegap.bitnagil.data.onboarding.model.request.GetOnBoardingRecommendRoutinesRequest
import com.threegap.bitnagil.data.onboarding.model.response.GetOnBoardingRecommendRoutinesResponse

interface OnBoardingDataSource {
    suspend fun getOnBoardingList(): List<OnBoardingDto>
    suspend fun getOnBoardingRecommendRoutines(request: GetOnBoardingRecommendRoutinesRequest): Result<GetOnBoardingRecommendRoutinesResponse>
    suspend fun getOnBoardingAbstract(selectedOnBoardingItemIdList: List<Pair<String, List<String>>>): OnBoardingAbstractDto
    suspend fun registerRecommendRoutineList(selectedRecommendRoutineIds: List<Int>): Result<Unit>
}
