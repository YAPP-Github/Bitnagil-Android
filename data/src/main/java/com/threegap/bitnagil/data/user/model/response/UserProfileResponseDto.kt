package com.threegap.bitnagil.data.user.model.response

import com.threegap.bitnagil.domain.user.model.UserProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponseDto(
    @SerialName("nickname")
    val nickname: String,
)

fun UserProfileResponseDto.toDomain() =
    UserProfile(
        nickname = this.nickname,
    )
