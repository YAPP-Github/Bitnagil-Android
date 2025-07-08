package com.threegap.bitnagil.di.core

import com.threegap.bitnagil.BuildConfig
import com.threegap.bitnagil.datastore.auth.storage.AuthTokenDataStore
import com.threegap.bitnagil.network.auth.AuthInterceptor
import com.threegap.bitnagil.network.token.TokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val APPLICATION_JSON = "application/json"

    @Provides
    @Singleton
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            explicitNulls = false
        }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory(APPLICATION_JSON.toMediaType())

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideTokenStore(dataStore: AuthTokenDataStore): TokenProvider =
        object : TokenProvider {
            override suspend fun getAccessToken(): String? = dataStore.tokenFlow.first().accessToken
        }

    @Provides
    @Singleton
    @Auth
    fun provideAuthInterceptor(tokenProvider: TokenProvider): Interceptor =
        AuthInterceptor(tokenProvider)

    @Provides
    @Singleton
    @Auth
    fun provideAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Auth authInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(10L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    @NoneAuth
    fun provideNoneAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(10L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    @Auth
    fun provideAuthRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        @Auth okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    @NoneAuth
    fun provideNoneAuthRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        @NoneAuth okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()
}
