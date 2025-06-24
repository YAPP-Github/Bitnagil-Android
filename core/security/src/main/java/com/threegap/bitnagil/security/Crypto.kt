package com.threegap.bitnagil.security

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

class Crypto(
    private val keyProvider: KeyProvider,
    private val transformation: String = "AES/CBC/PKCS7Padding",
) {
    fun encrypt(bytes: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, keyProvider.getKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return iv + encrypted
    }

    fun decrypt(bytes: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(transformation)
        val iv = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, keyProvider.getKey(), IvParameterSpec(iv))
        return cipher.doFinal(data)
    }
}
