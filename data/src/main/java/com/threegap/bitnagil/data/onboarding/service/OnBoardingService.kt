package com.threegap.bitnagil.data.onboarding.service

import com.threegap.bitnagil.data.onboarding.model.request.GetOnBoardingRecommendRoutinesRequest
import com.threegap.bitnagil.data.onboarding.model.response.GetOnBoardingRecommendRoutinesResponse
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OnBoardingService {
    @POST("/api/v1/onboarding")
    suspend fun postOnBoarding(
        @Body onBoardingRecommendRoutinesRequest: GetOnBoardingRecommendRoutinesRequest
    ): BaseResponse<GetOnBoardingRecommendRoutinesResponse>
}
