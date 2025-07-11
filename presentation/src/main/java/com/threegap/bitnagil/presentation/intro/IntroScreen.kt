package com.threegap.bitnagil.presentation.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun IntroScreenContainer() {
    IntroScreen()
}

@Composable
private fun IntroScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Text("인트로 화면")
    }
}

@Preview
@Composable
private fun IntroScreenPreview() {
    IntroScreen()
}
