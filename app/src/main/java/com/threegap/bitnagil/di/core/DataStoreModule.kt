package com.threegap.bitnagil.di.core

import android.content.Context
import com.threegap.bitnagil.datastore.auth.crypto.TokenCrypto
import com.threegap.bitnagil.datastore.auth.serializer.AuthTokenSerializer
import com.threegap.bitnagil.datastore.auth.serializer.AuthTokenSerializerImpl
import com.threegap.bitnagil.datastore.auth.storage.AuthTokenDataStore
import com.threegap.bitnagil.datastore.auth.storage.AuthTokenStorageFactory
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

    @Singleton
    @Provides
    fun provideTokenCrypto(crypto: Crypto): TokenCrypto =
        object : TokenCrypto {
            override fun encrypt(bytes: ByteArray): ByteArray = crypto.encrypt(bytes)

            override fun decrypt(bytes: ByteArray): ByteArray = crypto.decrypt(bytes)
        }

    @Provides
    @Singleton
    fun provideAuthTokenSerializer(tokenCrypto: TokenCrypto): AuthTokenSerializer =
        AuthTokenSerializerImpl(tokenCrypto)

    @Provides
    @Singleton
    fun provideAuthTokenStorage(
        @ApplicationContext context: Context,
        serializer: AuthTokenSerializer,
    ): AuthTokenDataStore = AuthTokenStorageFactory.create(context, serializer)
}
