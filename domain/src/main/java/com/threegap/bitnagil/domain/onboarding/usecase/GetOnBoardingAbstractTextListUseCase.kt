package com.threegap.bitnagil.domain.onboarding.usecase

import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstractText
import com.threegap.bitnagil.domain.onboarding.model.OnBoardingAbstractTextItem
import javax.inject.Inject

class GetOnBoardingAbstractTextListUseCase @Inject constructor() {
    operator fun invoke(selectedOnBoardingItemIdLists: List<List<String>>): List<OnBoardingAbstractText> {
        return selectedOnBoardingItemIdLists.mapNotNull {
            if ((it.firstOrNull()?.startsWith("1") == true || it.firstOrNull()?.startsWith("4") == true)) {
                getSingleSelectOnBoardingAbstractText(it.first())
            } else if (it.firstOrNull()?.startsWith("3") == true) {
                getMultipleSelectOnBoardingAbstractText(it)
            } else {
                null
            }
        }
    }

    private fun getSingleSelectOnBoardingAbstractText(onBoardingItemId: String): OnBoardingAbstractText {
        return when(onBoardingItemId) {
            "1_1" -> {
                OnBoardingAbstractText(
                    onBoardingId = onBoardingItemId,
                    textItemList = listOf(
                        OnBoardingAbstractTextItem(
                            text = "아침루틴",
                            isBold = true,
                        ),
                        OnBoardingAbstractTextItem(
                            text = "을 만들고 싶고",
                            isBold = false,
                        ),
                    )
                )
            }
            "1_2" -> {
                OnBoardingAbstractText(
                    onBoardingId = onBoardingItemId,
                    textItemList = listOf(
                        OnBoardingAbstractTextItem(
                            text = "저녁루틴",
                            isBold = true,
                        ),
                        OnBoardingAbstractTextItem(
                            text = "을 만들고 싶고",
                            isBold = false,
                        ),
                    )
                )
            }
            "1_3" -> {
                OnBoardingAbstractText(
                    onBoardingId = onBoardingItemId,
                    textItemList = listOf(
                        OnBoardingAbstractTextItem(
                            text = "전체 루틴",
                            isBold = true,
                        ),
                        OnBoardingAbstractTextItem(
                            text = "을 회복하고 싶고",
                            isBold = false,
                        ),
                    )
                )
            }
            "4_1" -> {
                OnBoardingAbstractText(
                    onBoardingId = onBoardingItemId,
                    textItemList = listOf(
                        OnBoardingAbstractTextItem(
                            text = "주 1회 외출을",
                            isBold = true,
                        ),
                        OnBoardingAbstractTextItem(
                            text = " 목표로 해볼꼐요!",
                            isBold = false,
                        ),
                    )
                )
            }
            "4_2" -> {
                OnBoardingAbstractText(
                    onBoardingId = onBoardingItemId,
                    textItemList = listOf(
                        OnBoardingAbstractTextItem(
                            text = "주 3회 외출을",
                            isBold = true,
                        ),
                        OnBoardingAbstractTextItem(
                            text = " 목표로 해볼꼐요!",
                            isBold = false,
                        ),
                    )
                )
            }
            "4_3" -> {
                OnBoardingAbstractText(
                    onBoardingId = onBoardingItemId,
                    textItemList = listOf(
                        OnBoardingAbstractTextItem(
                            text = "주 4회 이상 외출을",
                            isBold = true,
                        ),
                        OnBoardingAbstractTextItem(
                            text = " 목표로 해볼꼐요!",
                            isBold = false,
                        ),
                    )
                )
            }
            "4_4" -> {
                OnBoardingAbstractText(
                    onBoardingId = onBoardingItemId,
                    textItemList = listOf(
                        OnBoardingAbstractTextItem(
                            text = "최소한의 외출을",
                            isBold = true,
                        ),
                        OnBoardingAbstractTextItem(
                            text = " 목표로 해볼꼐요!",
                            isBold = false,
                        ),
                    )
                )
            }
            else -> {
                throw IllegalArgumentException("Invalid onBoardingItemId: $onBoardingItemId")
            }
        }
    }

    private fun getMultipleSelectOnBoardingAbstractText(onBoardingItemIdList: List<String>): OnBoardingAbstractText {
        val onBoardingAbstractTextItemList = mutableListOf<OnBoardingAbstractTextItem>()
        for (onBoardingItemId in onBoardingItemIdList) {
            when(onBoardingItemId) {
                "3_1" -> {
                    onBoardingAbstractTextItemList.apply {
                        add(
                            OnBoardingAbstractTextItem(
                                text = "안정감",
                                isBold = true,
                            )
                        )
                        add(
                            OnBoardingAbstractTextItem(
                                text = ", ",
                                isBold = false,
                            )
                        )
                    }
                }
                "3_2" -> {
                    onBoardingAbstractTextItemList.apply {
                        add(
                            OnBoardingAbstractTextItem(
                                text = "연결감",
                                isBold = true,
                            )
                        )
                        add(
                            OnBoardingAbstractTextItem(
                                text = ", ",
                                isBold = false,
                            )
                        )
                    }
                }
                "3_3" -> {
                    onBoardingAbstractTextItemList.apply {
                        add(
                            OnBoardingAbstractTextItem(
                                text = "성장감",
                                isBold = true,
                            )
                        )
                        add(
                            OnBoardingAbstractTextItem(
                                text = ", ",
                                isBold = false,
                            )
                        )
                    }
                }
                "3_4" -> {
                    onBoardingAbstractTextItemList.apply {
                        add(
                            OnBoardingAbstractTextItem(
                                text = "생동감",
                                isBold = true,
                            )
                        )
                        add(
                            OnBoardingAbstractTextItem(
                                text = ", ",
                                isBold = false,
                            )
                        )
                    }
                }
            }
        }

        onBoardingAbstractTextItemList.removeLastOrNull()
        onBoardingAbstractTextItemList.add(
            OnBoardingAbstractTextItem(
                text = "을 원하는 중이에요.",
                isBold = false
            )
        )

        return OnBoardingAbstractText(
            onBoardingId = "3",
            textItemList = onBoardingAbstractTextItemList
        )
    }
}
