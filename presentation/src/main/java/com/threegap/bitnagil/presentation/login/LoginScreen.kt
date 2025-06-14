package com.threegap.bitnagil.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .fillMaxSize()
                .background(Color.White),
    ) {
        Text(text = "여긴 로그인 화면")

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onLoginClick,
        ) {
            Text("로그인 버튼 눌러보던가")
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    BitnagilTheme {
        LoginScreen(
            onLoginClick = {},
        )
    }
}
