package com.threegap.bitnagil.security.crypto

interface Crypto {
    fun encrypt(bytes: ByteArray): ByteArray

    fun decrypt(bytes: ByteArray): ByteArray
}
