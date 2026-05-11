package com.threegap.bitnagil.presentation.util.statusbar

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavStatusBarEffect(navController: NavController) {
    val activity = LocalActivity.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        navBackStackEntry?.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            activity?.window?.let { window ->
                StatusBarAppearanceManager.applyStatusBarColorByLuminance(window)
            }
        }
    }
}
