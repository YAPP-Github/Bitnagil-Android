package com.threegap.bitnagil.data.user.repositoryImpl

import com.threegap.bitnagil.data.user.datasource.UserLocalDataSource
import com.threegap.bitnagil.data.user.datasource.UserRemoteDataSource
import com.threegap.bitnagil.data.user.model.response.toDomain
import com.threegap.bitnagil.domain.user.model.UserProfile
import com.threegap.bitnagil.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    private val fetchMutex = Mutex()

    override fun observeUserProfile(): Flow<Result<UserProfile>> = flow {
        fetchAndCacheIfNeeded().onFailure {
            emit(Result.failure(it))
            return@flow
        }

        emitAll(
            userLocalDataSource.userProfile
                .filterNotNull()
                .map { Result.success(it) },
        )
    }

    override suspend fun getUserProfile(): Result<UserProfile> {
        return fetchAndCacheIfNeeded()
    }

    override fun clearCache() {
        userLocalDataSource.clearCache()
    }

    private suspend fun fetchAndCacheIfNeeded(): Result<UserProfile> {
        userLocalDataSource.userProfile.value?.let { return Result.success(it) }

        return fetchMutex.withLock {
            userLocalDataSource.userProfile.value?.let { return@withLock Result.success(it) }

            userRemoteDataSource.fetchUserProfile()
                .onSuccess { response ->
                    userLocalDataSource.saveUserProfile(response.toDomain())
                }
                .map { it.toDomain() }
        }
    }
}
