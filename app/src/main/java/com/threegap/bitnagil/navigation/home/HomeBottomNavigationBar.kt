package com.threegap.bitnagil.navigation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.threegap.bitnagil.designsystem.BitnagilTheme

@Composable
fun HomeBottomNavigationBar(
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BitnagilTheme.colors.white)
            .padding(horizontal = 16.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        HomeRoute.entries.map { homeRoute ->
            HomeBottomNavigationItem(
                modifier = Modifier.weight(1f),
                selectIconResourceId = homeRoute.selectIconResourceId,
                unSelectIconResourceId = homeRoute.unSelectIconResourceId,
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

@Composable
private fun HomeBottomNavigationItem(
    modifier: Modifier = Modifier,
    selectIconResourceId: Int,
    unSelectIconResourceId: Int,
    title: String,
    onClick: () -> Unit,
    selected: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val contentTintColor = when {
        isPressed -> BitnagilTheme.colors.navy300
        selected -> BitnagilTheme.colors.navy500
        else -> BitnagilTheme.colors.navy100
    }
    val iconResourceId = if (selected) selectIconResourceId else unSelectIconResourceId

    Column(
        modifier = modifier.clickable(
            onClick = onClick,
            interactionSource = interactionSource,
            indication = null,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = iconResourceId),
            contentDescription = title,
            modifier = Modifier.padding(4.dp).size(24.dp),
            colorFilter = ColorFilter.tint(color = contentTintColor),
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
