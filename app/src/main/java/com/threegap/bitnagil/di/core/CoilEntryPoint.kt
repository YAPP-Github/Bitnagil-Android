package com.threegap.bitnagil.di.core

import com.threegap.bitnagil.network.NoneAuth
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CoilEntryPoint {
    @NoneAuth
    fun noneAuthOkHttpClient(): OkHttpClient
}
