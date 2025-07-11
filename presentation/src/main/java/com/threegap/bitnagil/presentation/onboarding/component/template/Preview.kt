package com.threegap.bitnagil.presentation.onboarding.component.template

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.threegap.bitnagil.presentation.onboarding.model.OnBoardingItem

@Preview(showBackground = true)
@Composable
fun OnBoardingTemplatePreview() {
    OnBoardingSelectTemplate(
        title = "title",
        subText = "subText",
        items = listOf(
            OnBoardingItem(
                id = "id",
                title = "title1",
                description = "description1",
                selected = true,
            ),
            OnBoardingItem(
                id = "id",
                title = "title2",
                description = "description2",
                selected = false,
            ),
            OnBoardingItem(
                id = "id",
                title = "title3",
                description = "description3",
                selected = true,
            ),
            OnBoardingItem(
                id = "id",
                title = "title4",
                description = "description4",
                selected = false,
            ),
        ),
        onClickNextButton = {},
        onClickItem = {}
    )
}
