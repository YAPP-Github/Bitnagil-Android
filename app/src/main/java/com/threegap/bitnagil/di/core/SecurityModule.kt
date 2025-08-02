package com.threegap.bitnagil.di.core

import com.threegap.bitnagil.security.crypto.Crypto
import com.threegap.bitnagil.security.crypto.SecureCrypto
import com.threegap.bitnagil.security.keystore.AndroidKeyProvider
import com.threegap.bitnagil.security.keystore.KeyProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
    fun provideKeyProvider(): KeyProvider = AndroidKeyProvider()

    @Provides
    @Singleton
    fun provideCrypto(keyProvider: KeyProvider): Crypto = SecureCrypto(keyProvider)
}
