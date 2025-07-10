package com.threegap.bitnagil.data.common

import com.threegap.bitnagil.domain.error.model.BitnagilError
import com.threegap.bitnagil.network.model.BaseResponse
import com.threegap.bitnagil.network.model.ErrorResponse
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

internal suspend inline fun <T> safeApiCall(
    crossinline apiCall: suspend () -> BaseResponse<T>,
): Result<T> {
    return try {
        val response = apiCall()
        response.data?.let { data ->
            Result.success(data)
        } ?: Result.failure(
            BitnagilError(
                code = "EMPTY_DATA",
                message = response.message,
            ),
        )
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody()?.string()
        val errorResponse = errorBody?.let {
            Json.decodeFromString<ErrorResponse>(it)
        }
        Result.failure(
            BitnagilError(
                code = errorResponse?.code ?: "HTTP_${e.code()}",
                message = errorResponse?.message ?: e.message(),
            ),
        )
    } catch (e: IOException) {
        Result.failure(Exception(e.message ?: "Network error"))
    } catch (e: Exception) {
        Result.failure(Exception(e.message ?: "Unknown error"))
    }
}
