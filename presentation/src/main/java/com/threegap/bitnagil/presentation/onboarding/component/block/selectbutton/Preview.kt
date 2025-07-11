package com.threegap.bitnagil.presentation.onboarding.component.block.selectbutton

import OnBoardingSelectButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun OnBoardingSelectButtonPreview() {

    Column {
        OnBoardingSelectButton(
            title = "루틴명",
            description = "세부 루틴 한 줄 설명",
            onClick = {},
            iconResourceId = android.R.drawable.ic_menu_search,
        )

        Spacer(modifier = Modifier.height(12.dp))

        OnBoardingSelectButton(
            title = "루틴명",
            description = "세부 루틴 한 줄 설명",
            onClick = {},
        )

        Spacer(modifier = Modifier.height(12.dp))

        OnBoardingSelectButton(
            title = "루틴명",
            description = null,
            onClick = {},
        )
    }
}
