package com.threegap.bitnagil.domain.user.usecase

import com.threegap.bitnagil.domain.user.model.UserProfile
import com.threegap.bitnagil.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<Result<UserProfile>> = userRepository.observeUserProfile()
}
