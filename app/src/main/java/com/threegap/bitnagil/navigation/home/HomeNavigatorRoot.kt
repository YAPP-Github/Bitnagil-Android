package com.threegap.bitnagil.navigation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeNavigatorRoot() {
    val homeNavigator = rememberHomeNavigator()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            HomeBottomNavigationBar(navController = homeNavigator.navController)
        },
        content = { _ ->
            HomeNavHost(
                modifier = Modifier.fillMaxSize(),
                navigator = homeNavigator,
            )
        }
    )
}
