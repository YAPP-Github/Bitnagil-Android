package com.threegap.bitnagil

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: MainNavigator = rememberMainNavigator(),
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        MainNavHost(
            navigator = navigator,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
