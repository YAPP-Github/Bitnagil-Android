package com.threegap.bitnagil.presentation.onboarding.component.atom.iconbutton

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun IconButtonPreview() {
    IconButton(
        svgResourceId = android.R.drawable.ic_menu_search,
        contentDescription = "Search Icon",
        onClick = {},
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun IconButtonPressedPreview() {
    IconButton(
        svgResourceId = android.R.drawable.ic_menu_add,
        contentDescription = "Add Icon",
        onClick = {},
    )
}
