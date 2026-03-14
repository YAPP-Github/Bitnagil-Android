package com.threegap.bitnagil.data.user.repositoryImpl

import com.threegap.bitnagil.data.user.datasource.UserLocalDataSource
import com.threegap.bitnagil.data.user.datasource.UserRemoteDataSource
import com.threegap.bitnagil.data.user.model.response.UserProfileResponse
import com.threegap.bitnagil.domain.user.model.UserProfile
import com.threegap.bitnagil.domain.user.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger

class UserRepositoryImplTest {

    private lateinit var localDataSource: FakeUserLocalDataSource
    private lateinit var remoteDataSource: FakeUserRemoteDataSource
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        localDataSource = FakeUserLocalDataSource()
        remoteDataSource = FakeUserRemoteDataSource()
        userRepository = UserRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Test
    fun `캐시가 비어있을 때 observeUserProfile을 구독하면 Remote에서 데이터를 가져와 캐시를 업데이트해야 한다`() =
        runTest {
            // given
            val expectedProfile = UserProfile(nickname = "TestUser")
            remoteDataSource.profileResponse = UserProfileResponse(nickname = "TestUser")

            // when
            // 구독(first)이 시작되는 순간 Fetch가 발생함
            val result = userRepository.observeUserProfile().first()

            // then
            assertEquals(expectedProfile, result.getOrNull())
            assertEquals(1, remoteDataSource.fetchCount.get())
            assertEquals(expectedProfile, localDataSource.userProfile.value)
        }

    @Test
    fun `캐시가 이미 존재할 때 observeUserProfile을 구독하면 Remote를 호출하지 않고 캐시를 반환해야 한다`() =
        runTest {
            // given
            val cachedProfile = UserProfile(nickname = "CachedUser")
            localDataSource.saveUserProfile(cachedProfile)

            // when
            val result = userRepository.observeUserProfile().first()

            // then
            assertEquals(cachedProfile, result.getOrNull())
            assertEquals(0, remoteDataSource.fetchCount.get())
        }

    @Test
    fun `여러 코루틴이 동시에 observeUserProfile을 구독해도 Remote API는 1회만 호출되어야 한다 (Race Condition 방지)`() =
        runTest {
            // given
            remoteDataSource.profileResponse = UserProfileResponse(nickname = "RaceUser")
            remoteDataSource.delayMillis = 100L // 네트워크 지연 시뮬레이션

            // when
            // 10개의 코루틴이 동시에 구독 시작
            val jobs = List(10) {
                async { userRepository.observeUserProfile().first() }
            }
            jobs.awaitAll()

            // then
            assertEquals(1, remoteDataSource.fetchCount.get())
            assertEquals("RaceUser", localDataSource.userProfile.value?.nickname)
        }

    @Test
    fun `clearCache를 호출하면 로컬 캐시가 초기화되어야 한다`() =
        runTest {
            // given
            localDataSource.saveUserProfile(UserProfile(nickname = "ToDelete"))

            // when
            userRepository.clearCache()

            // then
            assertEquals(null, localDataSource.userProfile.value)
        }

    // --- Fake Objects ---

    private class FakeUserLocalDataSource : UserLocalDataSource {
        private val _userProfile = MutableStateFlow<UserProfile?>(null)
        override val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

        override suspend fun saveUserProfile(userProfile: UserProfile) {
            _userProfile.update { userProfile }
        }

        override fun clearCache() {
            _userProfile.update { null }
        }
    }

    private class FakeUserRemoteDataSource : UserRemoteDataSource {
        var profileResponse: UserProfileResponse? = null
        val fetchCount = AtomicInteger(0)
        var delayMillis = 0L

        override suspend fun fetchUserProfile(): Result<UserProfileResponse> {
            if (delayMillis > 0) delay(delayMillis)
            fetchCount.incrementAndGet()
            return profileResponse?.let { Result.success(it) }
                ?: Result.failure(Exception("No profile set in fake"))
        }
    }
}
