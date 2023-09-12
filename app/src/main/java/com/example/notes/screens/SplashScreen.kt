package com.example.notes.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.R
import com.example.notes.data.splash.SplashScreenUIEvents
import com.example.notes.data.splash.SplashScreenViewModel
import com.example.notes.uicomponents.AppImage
import javax.inject.Inject

@Composable
fun SplashScreen(
    navigationController: NavHostController,
    splashScreenViewModel: SplashScreenViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        Handler(Looper.myLooper()!!).postDelayed({
            splashScreenViewModel.onEvent(SplashScreenUIEvents.OnLoaded(navigationController))
        }, 3000)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = .8f)),
        contentAlignment = Alignment.Center
    ) {
        AppImage(
            painterResource(id = R.drawable.notes),
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
        )
    }
}