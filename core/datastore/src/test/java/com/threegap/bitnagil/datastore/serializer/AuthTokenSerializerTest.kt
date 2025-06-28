package com.threegap.bitnagil.datastore.serializer

import com.threegap.bitnagil.datastore.model.AuthToken
import com.threegap.bitnagil.security.crypto.Crypto
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.Base64

class AuthTokenSerializerTest {
    private lateinit var serializer: AuthTokenSerializer
    private lateinit var crypto: FakeCrypto
    private lateinit var fakeToken: AuthToken
    private lateinit var encrypted: ByteArray
    private lateinit var json: String

    private class FakeCrypto(
        private val encryptResult: ByteArray,
        private val decryptResult: ByteArray,
        private val shouldFailDecrypt: Boolean = false,
    ) : Crypto {
        override fun encrypt(bytes: ByteArray): ByteArray = encryptResult

        override fun decrypt(bytes: ByteArray): ByteArray {
            if (shouldFailDecrypt) throw RuntimeException("복호화 실패")
            return decryptResult
        }
    }

    @Before
    fun setUp() {
        fakeToken = AuthToken("access", "refresh")
        json = Json.encodeToString(fakeToken)
        encrypted = "암호화된값".toByteArray()

        crypto =
            FakeCrypto(
                encryptResult = encrypted,
                decryptResult = json.toByteArray(),
            )

        serializer = AuthTokenSerializer(crypto)
    }

    @Test
    fun `writeTo는 AuthToken을 직렬화하여 기록한다`() =
        runTest {
            // given
            val outputStream = ByteArrayOutputStream()

            // when
            serializer.writeTo(fakeToken, outputStream)

            // then
            val expected = Base64.getEncoder().encode(encrypted)
            assertEquals(expected.toList(), outputStream.toByteArray().toList())
        }

    @Test
    fun `readFrom은 InputStream을 역직렬화하여 AuthToken으로 복원한다`() =
        runTest {
            // given
            val input = Base64.getEncoder().encode(encrypted)
            val inputStream = ByteArrayInputStream(input)

            // when
            val result = serializer.readFrom(inputStream)

            // then
            assertEquals(fakeToken, result)
        }

    @Test
    fun `readFrom에서 예외 발생시 기본값을 반환한다`() =
        runTest {
            // given
            val brokenCrypto =
                FakeCrypto(
                    encryptResult = byteArrayOf(),
                    decryptResult = byteArrayOf(),
                    shouldFailDecrypt = true,
                )
            val brokenSerializer = AuthTokenSerializer(brokenCrypto)
            val inputStream = ByteArrayInputStream(Base64.getEncoder().encode(encrypted))

            // when
            val result = brokenSerializer.readFrom(inputStream)

            // then
            assertEquals(AuthToken(), result)
        }
}
