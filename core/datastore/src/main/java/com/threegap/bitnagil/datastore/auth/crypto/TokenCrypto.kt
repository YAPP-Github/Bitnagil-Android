package com.threegap.bitnagil.datastore.auth.crypto

interface TokenCrypto {
    fun encrypt(bytes: ByteArray): ByteArray
    fun decrypt(bytes: ByteArray): ByteArray
}
