package com.threegap.bitnagil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.threegap.bitnagil.designsystem.component.atom.BitnagilToastContainer
import com.threegap.bitnagil.designsystem.component.atom.rememberBitnagilToast
import com.threegap.bitnagil.presentation.common.toast.GlobalBitnagilToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainNavigator = rememberMainNavigator()
            val globalToast = rememberBitnagilToast()

            LaunchedEffect(globalToast) {
                GlobalBitnagilToast.initialize(globalToast)
            }

            Box(modifier = Modifier.fillMaxSize()) {
                MainScreen(
                    navigator = mainNavigator,
                )

                BitnagilToastContainer(
                    state = globalToast,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 100.dp),
                )
            }
        }
    }
}
