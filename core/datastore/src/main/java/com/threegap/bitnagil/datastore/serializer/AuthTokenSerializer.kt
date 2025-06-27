package com.threegap.bitnagil.datastore.serializer

import com.threegap.bitnagil.datastore.model.AuthToken
import com.threegap.bitnagil.security.crypto.Crypto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64
import javax.inject.Inject

internal class AuthTokenSerializer
    @Inject
    constructor(
        private val crypto: Crypto,
    ) : TokenSerializer {
        override val defaultValue: AuthToken
            get() = AuthToken()

        override suspend fun readFrom(input: InputStream): AuthToken {
            return try {
                val encryptedBytes =
                    withContext(Dispatchers.IO) {
                        input.use { it.readBytes() }
                    }
                val decodedBytes = Base64.getDecoder().decode(encryptedBytes)
                val decryptedBytes = crypto.decrypt(decodedBytes)
                val decodedJsonString = decryptedBytes.decodeToString()
                Json.decodeFromString(decodedJsonString)
            } catch (e: Exception) {
                AuthToken()
            }
        }

        override suspend fun writeTo(
            t: AuthToken,
            output: OutputStream,
        ) {
            val json = Json.encodeToString(t)
            val bytes = json.toByteArray()
            val encryptedBytes = crypto.encrypt(bytes)
            val encryptedBytesBase64 = Base64.getEncoder().encode(encryptedBytes)
            withContext(Dispatchers.IO) {
                output.use {
                    it.write(encryptedBytesBase64)
                }
            }
        }
    }
