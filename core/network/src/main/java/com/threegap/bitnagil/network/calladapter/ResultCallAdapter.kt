package com.threegap.bitnagil.network.calladapter

import com.threegap.bitnagil.network.model.BaseResponse
import com.threegap.bitnagil.network.model.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be parameterized as Call<Result<Foo>>" }

        val responseType = getParameterUpperBound(0, returnType)

        if (getRawType(responseType) != Result::class.java) return null
        check(responseType is ParameterizedType) { "Response must be parameterized as Result<Foo>" }

        val successType = getParameterUpperBound(0, responseType)

        val baseResponseType = object : ParameterizedType {
            override fun getRawType(): Type = BaseResponse::class.java
            override fun getOwnerType(): Type? = null
            override fun getActualTypeArguments(): Array<Type> = arrayOf(successType)
        }

        val isUnit = getRawType(successType) == Unit::class.java

        val errorBodyConverter: Converter<ResponseBody, ErrorResponse> =
            retrofit.responseBodyConverter(ErrorResponse::class.java, annotations)

        return ResultCallAdapter<Any>(baseResponseType, isUnit, errorBodyConverter)
    }
}

class ResultCallAdapter<T>(
    private val responseType: Type,
    private val isUnit: Boolean,
    private val errorBodyConverter: Converter<ResponseBody, ErrorResponse>,
) : CallAdapter<BaseResponse<T>, Call<Result<T>>> {
    override fun responseType(): Type = responseType
    override fun adapt(call: Call<BaseResponse<T>>): Call<Result<T>> =
        ResultCall(call, isUnit, errorBodyConverter)
}
