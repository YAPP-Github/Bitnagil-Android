package com.threegap.bitnagil.security.crypto

import com.threegap.bitnagil.security.keystore.KeyProvider
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

internal class SecureCrypto(
    private val keyProvider: KeyProvider,
    private val transformation: String = "AES/CBC/PKCS7Padding",
) : Crypto {
    override fun encrypt(bytes: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(transformation)
        cipher.init(Cipher.ENCRYPT_MODE, keyProvider.getKey())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(bytes)
        return iv + encrypted
    }

    override fun decrypt(bytes: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(transformation)
        require(bytes.size >= cipher.blockSize) {
            INVALID_INPUT_TOO_SHORT_MSG
        }
        val iv = bytes.copyOfRange(0, cipher.blockSize)
        val data = bytes.copyOfRange(cipher.blockSize, bytes.size)
        cipher.init(Cipher.DECRYPT_MODE, keyProvider.getKey(), IvParameterSpec(iv))
        return cipher.doFinal(data)
    }

    companion object {
        private const val INVALID_INPUT_TOO_SHORT_MSG = "[ERROR] 복호화할 수 없는 입력입니다."
    }
}
