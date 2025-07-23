package com.threegap.bitnagil.data.common

import com.threegap.bitnagil.domain.error.model.BitnagilError
import com.threegap.bitnagil.network.model.BaseResponse
import com.threegap.bitnagil.network.model.ErrorResponse
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

internal suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> BaseResponse<T>,
): Result<T> =
    try {
        val response = apiCall()
        response.data?.let { data ->
            Result.success(data)
        } ?: Result.failure(
            BitnagilError(
                code = "EMPTY_DATA: ${response.code}",
                message = response.message,
            ),
        )
    } catch (e: Exception) {
        handleApiException(e)
    }


internal suspend inline fun safeUnitApiCall(
    crossinline apiCall: suspend () -> BaseResponse<Unit>,
): Result<Unit> =
    try {
        apiCall()
        Result.success(Unit)
    } catch (e: Exception) {
        handleApiException(e)
    }


private val json = Json { ignoreUnknownKeys = true }

private fun parseErrorResponse(errorBody: String?): ErrorResponse? =
    errorBody?.let {
        try {
            json.decodeFromString<ErrorResponse>(it)
        } catch (e: Exception) {
            null
        }
    }

private fun <T> handleApiException(e: Exception): Result<T> =
    when (e) {
        is HttpException -> {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = parseErrorResponse(errorBody)
            Result.failure(
                BitnagilError(
                    code = errorResponse?.code ?: "HTTP_${e.code()}",
                    message = errorResponse?.message ?: e.message(),
                )
            )
        }

        is IOException -> {
            Result.failure(Exception(e.message ?: "Network error"))
        }

        else -> {
            Result.failure(Exception(e.message ?: "Unknown error"))
        }
    }
