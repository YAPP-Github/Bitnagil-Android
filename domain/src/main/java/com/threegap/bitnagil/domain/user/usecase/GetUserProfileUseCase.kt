package com.threegap.bitnagil.domain.user.usecase

import com.threegap.bitnagil.domain.user.model.UserProfile
import com.threegap.bitnagil.domain.user.repository.UserRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(): Result<UserProfile> = userRepository.getUserProfile()
}
