package com.threegap.bitnagil.presentation.guide.model

import androidx.annotation.DrawableRes
import com.threegap.bitnagil.designsystem.R

enum class GuideType(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
) {
    REGISTER(
        title = "오늘 감정 등록하기란?",
        description = "오늘 느끼는 감정을 선택하면, 그 기분에 맞춰 추천 루틴을 확인할 수 있어요.",
        image = R.drawable.img_register_guide,
    ),
    HOME_LIST(
        title = "홈에서 더보기란?",
        description = "루틴리스트에서 세부 항목(세부루틴, 반복, 기간, 시간)을\n확인하고, 필요 시 수정하거나 삭제할 수 있어요.",
        image = R.drawable.img_list_guide,
    ),
    RECOMMEND(
        title = "맞춤 추천 루틴이란?",
        description = "처음 설정한 목표에 맞춰 루틴을 추천받을 수 있으며,\n필요할 때 언제든 수정할 수 있어요.",
        image = R.drawable.img_recommend_guide,
    ),
    EDIT(
        title = "루틴 수정하기란?",
        description = "등록한 루틴은 수정할 수 있으며, 변경 내용을 당일부터 또는 다음 날부터 적용하도록 선택할 수 있어요.",
        image = R.drawable.img_edit_guide,
    ),
}
