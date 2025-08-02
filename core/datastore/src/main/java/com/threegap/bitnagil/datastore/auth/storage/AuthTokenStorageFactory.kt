package com.threegap.bitnagil.datastore.auth.storage

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.threegap.bitnagil.datastore.auth.model.AuthToken
import com.threegap.bitnagil.datastore.auth.serializer.AuthTokenSerializer

object AuthTokenStorageFactory {
    fun create(context: Context, serializer: AuthTokenSerializer): AuthTokenDataStore {
        val dataStore = DataStoreFactory.create(
            serializer = serializer,
            produceFile = { context.dataStoreFile("auth-token.enc") },
            corruptionHandler = ReplaceFileCorruptionHandler { AuthToken() },
        )

        return AuthTokenDataStoreImpl(dataStore)
    }
}
