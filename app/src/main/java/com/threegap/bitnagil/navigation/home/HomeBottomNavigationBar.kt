package com.threegap.bitnagil.navigation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.threegap.bitnagil.designsystem.BitnagilTheme
import com.threegap.bitnagil.designsystem.component.atom.BitnagilIcon

@Composable
fun HomeBottomNavigationBar(
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = BitnagilTheme.colors.coolGray98,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BitnagilTheme.colors.white)
                .height(62.dp)
                .padding(horizontal = 16.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomeRoute.entries.map { homeRoute ->
                HomeBottomNavigationItem(
                    modifier = Modifier.weight(1f),
                    icon = homeRoute.icon,
                    title = homeRoute.title,
                    onClick = {
                        navController.navigate(homeRoute.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    selected = navBackStackEntry?.destination?.route == homeRoute.route,
                )
            }
        }
    }
}

@Composable
private fun HomeBottomNavigationItem(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    onClick: () -> Unit,
    selected: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val contentTintColor = when {
        isPressed -> BitnagilTheme.colors.coolGray10
        selected -> BitnagilTheme.colors.coolGray10
        else -> BitnagilTheme.colors.coolGray90
    }

    Column(
        modifier = modifier
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null,
            )
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        BitnagilIcon(
            id = icon,
            modifier = Modifier.size(24.dp),
            tint = contentTintColor,
        )

        Text(
            text = title,
            style = BitnagilTheme.typography.caption2Medium,
            color = contentTintColor,
        )
    }
}

@Composable
@Preview
private fun HomeBottomNavigationBarPreview() {
    val navigator = rememberHomeNavigator()

    HomeBottomNavigationBar(
        navController = navigator.navController,
    )
}
