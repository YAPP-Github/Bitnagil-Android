package com.threegap.bitnagil.datastore.storage

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import com.threegap.bitnagil.datastore.model.AuthToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.InputStream
import java.io.OutputStream

class AuthTokenDataStoreImplTest {
    @get:Rule
    val temporaryFolder: TemporaryFolder =
        TemporaryFolder
            .builder()
            .assureDeletion()
            .build()

    private lateinit var dataStore: DataStore<AuthToken>
    private lateinit var authTokenDataStore: AuthTokenDataStore

    private object FakeAuthTokenSerializer : Serializer<AuthToken> {
        override val defaultValue: AuthToken
            get() = AuthToken()

        override suspend fun readFrom(input: InputStream): AuthToken {
            return try {
                input.bufferedReader().use {
                    Json.decodeFromString(AuthToken.serializer(), it.readText())
                }
            } catch (e: Exception) {
                AuthToken()
            }
        }

        override suspend fun writeTo(
            t: AuthToken,
            output: OutputStream,
        ) {
            withContext(Dispatchers.IO) {
                output.writer().use {
                    it.write(Json.encodeToString(AuthToken.serializer(), t))
                }
            }
        }
    }

    @Before
    fun setup() {
        dataStore =
            DataStoreFactory.create(
                serializer = FakeAuthTokenSerializer,
                produceFile = { temporaryFolder.newFile("auth-token-test.enc") },
            )

        authTokenDataStore = AuthTokenDataStoreImpl(dataStore)
    }

    @Test
    fun `토큰 전체 업데이트가 성공하면 저장된 토큰을 반환해야 한다`() =
        runTest {
            // given
            val token =
                AuthToken(
                    accessToken = "access",
                    refreshToken = "refresh",
                )

            // when
            val result = authTokenDataStore.updateAuthToken(token)

            // then
            assertEquals(token, result)
        }

    @Test
    fun `accessToken만 업데이트하면 기존 refreshToken은 유지되어야 한다`() =
        runTest {
            // given
            authTokenDataStore.updateAuthToken(
                AuthToken(
                    accessToken = "oldAccess",
                    refreshToken = "oldRefresh",
                ),
            )

            // when
            val updated = authTokenDataStore.updateAccessToken(accessToken = "newAccess")

            // then
            assertEquals("newAccess", updated.accessToken)
            assertEquals("oldRefresh", updated.refreshToken)
        }

    @Test
    fun `refreshToken만 업데이트하면 기존 accessToken은 유지되어야 한다`() =
        runTest {
            // given
            authTokenDataStore.updateAuthToken(
                AuthToken(
                    accessToken = "oldAccess",
                    refreshToken = "oldRefresh",
                ),
            )

            // when
            val updated = authTokenDataStore.updateRefreshToken(refreshToken = "newRefresh")

            // then
            assertEquals("oldAccess", updated.accessToken)
            assertEquals("newRefresh", updated.refreshToken)
        }

    @Test
    fun `토큰을 클리어하면 기본값이 저장되어야 한다`() =
        runTest {
            // given
            authTokenDataStore.updateAuthToken(
                AuthToken(
                    accessToken = "someAccess",
                    refreshToken = "someRefresh",
                ),
            )

            // when
            val cleared = authTokenDataStore.clearAuthToken()

            // then
            assertEquals(AuthToken(), cleared)
        }

    @Test
    fun `tokenFlow는 현재 저장된 토큰을 방출해야 한다`() =
        runTest {
            // given
            val token =
                AuthToken(
                    accessToken = "flowAccess",
                    refreshToken = "flowRefresh",
                )

            // when
            authTokenDataStore.updateAuthToken(token)

            // then
            val flowValue = authTokenDataStore.tokenFlow.first()
            assertEquals(token, flowValue)
        }

    @Test(expected = RuntimeException::class)
    fun `updateAuthToken에서 예외 발생시 예외가 전파되어야 한다`() =
        runTest {
            // given
            val brokenStore =
                object : DataStore<AuthToken> {
                    override val data = flowOf(AuthToken())

                    override suspend fun updateData(transform: suspend (AuthToken) -> AuthToken): AuthToken {
                        throw RuntimeException("updateAuthToken failed")
                    }
                }
            val failingDataStore = AuthTokenDataStoreImpl(brokenStore)

            // when & then
            failingDataStore.updateAuthToken(AuthToken("access", "refresh"))
        }

    @Test(expected = RuntimeException::class)
    fun `updateAccessToken에서 예외 발생시 예외가 전파되어야 한다`() =
        runTest {
            // given
            val brokenStore =
                object : DataStore<AuthToken> {
                    override val data = flowOf(AuthToken())

                    override suspend fun updateData(transform: suspend (AuthToken) -> AuthToken): AuthToken {
                        throw RuntimeException("updateAccessToken failed")
                    }
                }
            val failingDataStore = AuthTokenDataStoreImpl(brokenStore)

            // when & then
            failingDataStore.updateAccessToken("newAccess")
        }

    @Test(expected = RuntimeException::class)
    fun `updateRefreshToken에서 예외 발생시 예외가 전파되어야 한다`() =
        runTest {
            // given
            val brokenStore =
                object : DataStore<AuthToken> {
                    override val data = flowOf(AuthToken())

                    override suspend fun updateData(transform: suspend (AuthToken) -> AuthToken): AuthToken {
                        throw RuntimeException("updateRefreshToken failed")
                    }
                }
            val failingDataStore = AuthTokenDataStoreImpl(brokenStore)

            // when & then
            failingDataStore.updateRefreshToken("newRefresh")
        }

    @Test(expected = RuntimeException::class)
    fun `clearAuthToken에서 예외 발생시 예외가 전파되어야 한다`() =
        runTest {
            // given
            val brokenStore =
                object : DataStore<AuthToken> {
                    override val data = flowOf(AuthToken())

                    override suspend fun updateData(transform: suspend (AuthToken) -> AuthToken): AuthToken {
                        throw RuntimeException("clearAuthToken failed")
                    }
                }
            val failingDataStore = AuthTokenDataStoreImpl(brokenStore)

            // when & then
            failingDataStore.clearAuthToken()
        }
}
