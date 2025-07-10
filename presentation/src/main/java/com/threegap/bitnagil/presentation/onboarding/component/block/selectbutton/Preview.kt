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
    // 실제 SVG 파일 경로 또는 URL로 대체해야 합니다.
    // 예시로 로컬 drawable 리소스의 이름을 사용합니다. (실제로는 유효한 svg 경로여야 합니다)
    // R.drawable.ic_android 와 같이 SVG 파일을 drawable 에 추가하고 사용하거나,
    // 네트워크 URL을 사용할 수 있습니다.
    // 여기서는 문자열 "sample.svg" 를 사용하지만, 실제 렌더링을 위해서는 유효한 경로가 필요합니다.

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
    }
}
