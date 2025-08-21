package com.threegap.bitnagil.data.onboarding.service

import com.threegap.bitnagil.data.onboarding.model.request.GetOnBoardingRecommendRoutinesRequest
import com.threegap.bitnagil.data.onboarding.model.request.RegisterOnBoardingRecommendRoutinesRequest
import com.threegap.bitnagil.data.onboarding.model.response.GetOnBoardingRecommendRoutinesResponse
import com.threegap.bitnagil.data.onboarding.model.response.GetUserOnBoardingResponse
import com.threegap.bitnagil.network.model.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OnBoardingService {
    @POST("/api/v2/onboardings")
    suspend fun postOnBoarding(
        @Body onBoardingRecommendRoutinesRequest: GetOnBoardingRecommendRoutinesRequest,
    ): BaseResponse<GetOnBoardingRecommendRoutinesResponse>

    @POST("/api/v2/onboardings/routines")
    suspend fun postOnBoardingRoutines(
        @Body registerOnBoardingRecommendRoutinesRequest: RegisterOnBoardingRecommendRoutinesRequest,
    ): BaseResponse<Unit>

    @GET("/api/v2/onboardings")
    suspend fun getOnBoarding(): BaseResponse<GetUserOnBoardingResponse>
}
