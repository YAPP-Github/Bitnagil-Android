package com.threegap.bitnagil.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.threegap.bitnagil.datastore.model.AuthToken
import com.threegap.bitnagil.datastore.serializer.AuthTokenSerializer
import com.threegap.bitnagil.datastore.serializer.TokenSerializer
import com.threegap.bitnagil.security.crypto.Crypto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideTokenSerializer(crypto: Crypto): TokenSerializer = AuthTokenSerializer(crypto)

    @Provides
    @Singleton
    fun provideAuthTokenDataStore(
        @ApplicationContext context: Context,
        tokenSerializer: TokenSerializer,
    ): DataStore<AuthToken> =
        DataStoreFactory.create(
            serializer = tokenSerializer,
            produceFile = { context.dataStoreFile("auth-token.enc") },
            corruptionHandler = ReplaceFileCorruptionHandler { AuthToken() },
        )
}
