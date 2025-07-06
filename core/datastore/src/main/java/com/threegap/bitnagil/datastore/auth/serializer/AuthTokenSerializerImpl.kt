package com.threegap.bitnagil.datastore.auth.serializer

import androidx.datastore.core.Serializer
import com.threegap.bitnagil.datastore.auth.crypto.TokenCrypto
import com.threegap.bitnagil.datastore.auth.model.AuthToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64

class AuthTokenSerializerImpl(
    private val crypto: TokenCrypto,
) : Serializer<AuthToken> {
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
