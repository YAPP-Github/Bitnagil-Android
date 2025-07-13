package com.threegap.bitnagil.data.onboarding.datasourceImpl

import com.threegap.bitnagil.data.common.safeApiCall
import com.threegap.bitnagil.data.onboarding.datasource.OnBoardingDataSource
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingAbstractDto
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingAbstractTextDto
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingAbstractTextItemDto
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingDto
import com.threegap.bitnagil.data.onboarding.model.dto.OnBoardingItemDto
import com.threegap.bitnagil.data.onboarding.model.request.GetOnBoardingRecommendRoutinesRequest
import com.threegap.bitnagil.data.onboarding.model.response.GetOnBoardingRecommendRoutinesResponse
import com.threegap.bitnagil.data.onboarding.service.OnBoardingService
import javax.inject.Inject

class OnBoardingDataSourceImpl @Inject constructor(
    private val onBoardingService: OnBoardingService,
) : OnBoardingDataSource {
    private val onBoardingDtoList = listOf(
        OnBoardingDto.TimeSlot,
        OnBoardingDto.RealOutingFrequency,
        OnBoardingDto.EmotionType,
        OnBoardingDto.TargetOutingFrequency,
    )

    override suspend fun getOnBoardingList(): List<OnBoardingDto> {
        return onBoardingDtoList
    }

    override suspend fun getOnBoardingRecommendRoutines(request: GetOnBoardingRecommendRoutinesRequest): Result<GetOnBoardingRecommendRoutinesResponse> {
        return safeApiCall {
            onBoardingService.postOnBoarding(request)
        }
    }

    override suspend fun getOnBoardingAbstract(selectedOnBoardingItemIdList: List<Pair<String, List<String>>>): OnBoardingAbstractDto {
        val onBoardingAbstractTextList = mutableListOf<OnBoardingAbstractTextDto>()

        for ((onBoardingId, selectedOnBoardingDetailIdList) in selectedOnBoardingItemIdList) {
            val onBoardingAbstractText = getOnBoardingAbstractText(onBoardingId, selectedOnBoardingDetailIdList)
            if (onBoardingAbstractText != null) {
                onBoardingAbstractTextList.add(onBoardingAbstractText)
            }
        }

        return OnBoardingAbstractDto(
            prefixText = "당신은 지금",
            detailTextsList = onBoardingAbstractTextList,
        )
    }

    private fun getOnBoardingAbstractText(onBoardingId: String, selectedOnBoardingDetailIdList: List<String>): OnBoardingAbstractTextDto? {
        if (selectedOnBoardingDetailIdList.isEmpty()) return null
        val onBoarding = onBoardingDtoList.find { it.id == onBoardingId }
        if (onBoarding == null) return null

        val isMultipleSelect = onBoarding.multipleSelectable
        return if (isMultipleSelect) {
            getMultipleSelectOnBoardingAbstractText(onBoardingId, selectedOnBoardingDetailIdList)
        } else {
            getSingleSelectOnBoardingAbstractText(onBoardingId, selectedOnBoardingDetailIdList.first())
        }
    }

    private fun getSingleSelectOnBoardingAbstractText(onBoardingId: String, onBoardingDetailId: String): OnBoardingAbstractTextDto? {
        when (onBoardingId) {
            OnBoardingDto.TimeSlot.id -> {
                return when (onBoardingDetailId) {
                    OnBoardingItemDto.Morning.id -> {
                        OnBoardingAbstractTextDto(
                            textItemList = listOf(
                                OnBoardingAbstractTextItemDto(
                                    text = "아침루틴",
                                    isBold = true,
                                ),
                                OnBoardingAbstractTextItemDto(
                                    text = "을 만들고 싶고",
                                    isBold = false,
                                ),
                            ),
                        )
                    }
                    OnBoardingItemDto.Evening.id -> {
                        OnBoardingAbstractTextDto(
                            textItemList = listOf(
                                OnBoardingAbstractTextItemDto(
                                    text = "저녁루틴",
                                    isBold = true,
                                ),
                                OnBoardingAbstractTextItemDto(
                                    text = "을 만들고 싶고",
                                    isBold = false,
                                ),
                            ),
                        )
                    }
                    OnBoardingItemDto.Nothing.id -> {
                        OnBoardingAbstractTextDto(
                            textItemList = listOf(
                                OnBoardingAbstractTextItemDto(
                                    text = "전체 루틴",
                                    isBold = true,
                                ),
                                OnBoardingAbstractTextItemDto(
                                    text = "을 회복하고 싶고",
                                    isBold = false,
                                ),
                            ),
                        )
                    }
                    else -> {
                        null
                    }
                }
            }
            OnBoardingDto.TargetOutingFrequency.id -> {
                return when (onBoardingDetailId) {
                    OnBoardingItemDto.TargetOutingOneToTwoPerWeek.id -> {
                        OnBoardingAbstractTextDto(
                            textItemList = listOf(
                                OnBoardingAbstractTextItemDto(
                                    text = "주 1회 외출을",
                                    isBold = true,
                                ),
                                OnBoardingAbstractTextItemDto(
                                    text = " 목표로 해볼게요!",
                                    isBold = false,
                                ),
                            ),
                        )
                    }
                    OnBoardingItemDto.TargetOutingThreeToFourPerWeek.id -> {
                        OnBoardingAbstractTextDto(
                            textItemList = listOf(
                                OnBoardingAbstractTextItemDto(
                                    text = "주3회 외출을",
                                    isBold = true,
                                ),
                                OnBoardingAbstractTextItemDto(
                                    text = " 목표로 해볼게요!",
                                    isBold = false,
                                ),
                            ),
                        )
                    }
                    OnBoardingItemDto.TargetOutingMoreThenFivePerWeek.id -> {
                        OnBoardingAbstractTextDto(
                            textItemList = listOf(
                                OnBoardingAbstractTextItemDto(
                                    text = "주 4회 이상 외출을",
                                    isBold = true,
                                ),
                                OnBoardingAbstractTextItemDto(
                                    text = " 목표로 해볼게요!",
                                    isBold = false,
                                ),
                            ),
                        )
                    }
                    OnBoardingItemDto.TargetOutingUnknown.id -> {
                        OnBoardingAbstractTextDto(
                            textItemList = listOf(
                                OnBoardingAbstractTextItemDto(
                                    text = "최소한의 외출을",
                                    isBold = true,
                                ),
                                OnBoardingAbstractTextItemDto(
                                    text = " 목표로 해볼게요!",
                                    isBold = false,
                                ),
                            ),
                        )
                    }
                    else -> {
                        null
                    }
                }
            }
            else -> {
                return null
            }
        }
    }

    private fun getMultipleSelectOnBoardingAbstractText(onBoardingId: String, onBoardingDetailIdList: List<String>): OnBoardingAbstractTextDto {
        val onBoardingAbstractTextItemList = mutableListOf<OnBoardingAbstractTextItemDto>()

        when (onBoardingId) {
            OnBoardingDto.EmotionType.id -> {
                for (onBoardingDetailId in onBoardingDetailIdList) {
                    when (onBoardingDetailId) {
                        OnBoardingItemDto.Stability.id -> {
                            onBoardingAbstractTextItemList.apply {
                                add(
                                    OnBoardingAbstractTextItemDto(
                                        text = "안정감",
                                        isBold = true,
                                    ),
                                )
                                add(
                                    OnBoardingAbstractTextItemDto(
                                        text = ", ",
                                        isBold = false,
                                    ),
                                )
                            }
                        }
                        OnBoardingItemDto.Connectedness.id -> {
                            onBoardingAbstractTextItemList.apply {
                                add(
                                    OnBoardingAbstractTextItemDto(
                                        text = "연결감",
                                        isBold = true,
                                    ),
                                )
                                add(
                                    OnBoardingAbstractTextItemDto(
                                        text = ", ",
                                        isBold = false,
                                    ),
                                )
                            }
                        }
                        OnBoardingItemDto.Vitality.id -> {
                            onBoardingAbstractTextItemList.apply {
                                add(
                                    OnBoardingAbstractTextItemDto(
                                        text = "생동감",
                                        isBold = true,
                                    ),
                                )
                                add(
                                    OnBoardingAbstractTextItemDto(
                                        text = ", ",
                                        isBold = false,
                                    ),
                                )
                            }
                        }
                        OnBoardingItemDto.Growth.id -> {
                            onBoardingAbstractTextItemList.apply {
                                add(
                                    OnBoardingAbstractTextItemDto(
                                        text = "성장감",
                                        isBold = true,
                                    ),
                                )
                                add(
                                    OnBoardingAbstractTextItemDto(
                                        text = ", ",
                                        isBold = false,
                                    ),
                                )
                            }
                        }
                    }
                }
            }
            else -> {
            }
        }

        onBoardingAbstractTextItemList.removeLastOrNull()
        onBoardingAbstractTextItemList.add(
            OnBoardingAbstractTextItemDto(
                text = "을 원하는 중이에요.",
                isBold = false,
            ),
        )

        return OnBoardingAbstractTextDto(
            textItemList = onBoardingAbstractTextItemList,
        )
    }
}
