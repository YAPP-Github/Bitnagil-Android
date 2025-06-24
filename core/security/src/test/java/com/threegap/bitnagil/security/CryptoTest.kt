package com.threegap.bitnagil.security

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class CryptoTest {
    private class FakeKeyProvider : KeyProvider {
        private val key: SecretKey =
            KeyGenerator
                .getInstance("AES")
                .apply { init(128) }
                .generateKey()

        override fun getKey(): SecretKey = key
    }

    private val crypto =
        Crypto(
            keyProvider = FakeKeyProvider(),
            transformation = "AES/CBC/PKCS5Padding",
        )

    @Test
    fun `암호화 후 복호화하면 원래 데이터와 같아야 한다`() {
        // given
        val original = "테스트 데이터".toByteArray()

        // when
        val encrypted = crypto.encrypt(original)
        val decrypted = crypto.decrypt(encrypted)

        // then
        assertEquals(String(original), String(decrypted))
    }

    @Test
    fun `같은 데이터를 암호화해도 결과는 달라야 한다`() {
        // given
        val input = "같은 입력".toByteArray()

        // when
        val encrypted1 = crypto.encrypt(input)
        val encrypted2 = crypto.encrypt(input)

        // then
        assertNotEquals(encrypted1.toList(), encrypted2.toList())
    }

    @Test
    fun `잘못된 데이터로 복호화를 시도하면 예외가 발생해야 한다`() {
        // given
        val invalid = ByteArray(4) { 0x00 }

        // when & then
        assertThrows(Exception::class.java) {
            crypto.decrypt(invalid)
        }
    }

    @Test
    fun `IV 일부가 조작된 경우 복호화하면 원래 데이터와 달라야 한다`() {
        // given
        val original = "iv 테스트".toByteArray()
        val encrypted = crypto.encrypt(original)
        encrypted[0] = encrypted[0].inc()

        // when
        val decrypted = crypto.decrypt(encrypted)

        // then
        assertNotEquals(String(original), String(decrypted))
    }

    @Test
    fun `암호화된 데이터가 조작된 경우 복호화 실패해야 한다`() {
        // given
        val original = "데이터 조작".toByteArray()
        val encrypted = crypto.encrypt(original)
        encrypted[encrypted.lastIndex] = encrypted.last().inc()

        // when & then
        assertThrows(Exception::class.java) {
            crypto.decrypt(encrypted)
        }
    }

    @Test
    fun `다른 키로 복호화하면 실패해야 한다`() {
        // given
        val original = "다른 키 테스트".toByteArray()
        val encrypted = crypto.encrypt(original)

        val otherKeyProvider =
            object : KeyProvider {
                override fun getKey(): SecretKey {
                    val keyGen = KeyGenerator.getInstance("AES")
                    keyGen.init(128)
                    return keyGen.generateKey()
                }
            }

        val otherCrypto = Crypto(otherKeyProvider, "AES/CBC/PKCS5Padding")

        // when & then
        assertThrows(Exception::class.java) {
            otherCrypto.decrypt(encrypted)
        }
    }
}
