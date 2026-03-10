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
        if (userLocalDataSource.userProfile.value == null) {
            fetchMutex.withLock {
                if (userLocalDataSource.userProfile.value == null) {
                    userRemoteDataSource.fetchUserProfile()
                        .onSuccess { response ->
                            userLocalDataSource.saveUserProfile(response.toDomain())
                        }
                        .onFailure {
                            emit(Result.failure(it))
                        }
                }
            }
        }

        emitAll(
            userLocalDataSource.userProfile
                .filterNotNull()
                .map { Result.success(it) },
        )
    }

    override fun clearCache() {
        userLocalDataSource.clearCache()
    }
}
