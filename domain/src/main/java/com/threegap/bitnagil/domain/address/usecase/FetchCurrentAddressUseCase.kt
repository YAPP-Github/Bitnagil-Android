package com.threegap.bitnagil.domain.address.usecase

import com.threegap.bitnagil.domain.address.model.CurrentAddress
import com.threegap.bitnagil.domain.address.repository.AddressRepository
import javax.inject.Inject

class FetchCurrentAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository,
) {
    suspend operator fun invoke(): Result<CurrentAddress> {
        return addressRepository.fetchCurrentLocation()
            .mapCatching { location ->
                val roadAddress = addressRepository.fetchCurrentAddress(
                    latitude = location.latitude,
                    longitude = location.longitude,
                ).getOrThrow()

                CurrentAddress(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    roadAddress = roadAddress,
                )
            }
    }
}
