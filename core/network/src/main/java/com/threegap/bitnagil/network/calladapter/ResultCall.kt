package com.threegap.bitnagil.network.calladapter

import com.threegap.bitnagil.network.model.BaseResponse
import com.threegap.bitnagil.network.model.ErrorResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import kotlin.coroutines.cancellation.CancellationException

class ResultCall<T>(
    private val delegate: Call<BaseResponse<T>>,
    private val isUnit: Boolean,
    private val errorBodyConverter: Converter<ResponseBody, ErrorResponse>,
) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(
            object : Callback<BaseResponse<T>> {
                override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {
                    val result = if (response.isSuccessful) {
                        val body = response.body()
                        when {
                            isUnit -> {
                                @Suppress("UNCHECKED_CAST")
                                Result.success(Unit as T)
                            }

                            body?.data != null -> Result.success(body.data)
                            else -> Result.failure(
                                ApiException(
                                    code = "EMPTY_DATA: ${response.code()}",
                                    message = body?.message ?: "Response body or data is null",
                                ),
                            )
                        }
                    } else {
                        val errorResponse = parseErrorResponse(response.errorBody())
                        Result.failure(
                            ApiException(
                                code = errorResponse?.code ?: "HTTP_${response.code()}",
                                message = errorResponse?.message ?: response.message(),
                            ),
                        )
                    }
                    callback.onResponse(this@ResultCall, Response.success(result))
                }

                override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                    if (t is CancellationException) return

                    callback.onResponse(this@ResultCall, Response.success(Result.failure(t)))
                }
            },
        )
    }

    private fun parseErrorResponse(errorBody: ResponseBody?): ErrorResponse? =
        errorBody?.let { body ->
            try {
                errorBodyConverter.convert(body)
            } catch (e: Exception) {
                null
            }
        }

    override fun clone(): Call<Result<T>> = ResultCall(delegate.clone(), isUnit, errorBodyConverter)
    override fun execute(): Response<Result<T>> = throw UnsupportedOperationException("ResultCall doesn't support execute()")
    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
}

class ApiException(val code: String, override val message: String) : Exception(message)
