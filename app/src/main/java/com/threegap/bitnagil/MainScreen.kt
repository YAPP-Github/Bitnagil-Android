package com.threegap.bitnagil

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun MainScreen(
    navigator: MainNavigator,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom),
        containerColor = BitnagilTheme.colors.white,
    ) { innerPadding ->
        MainNavHost(
            navigator = navigator,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
