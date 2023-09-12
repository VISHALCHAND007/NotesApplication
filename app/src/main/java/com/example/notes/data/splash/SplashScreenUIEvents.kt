package com.example.notes.data.splash

import androidx.navigation.NavHostController

sealed class SplashScreenUIEvents {
    data class OnLoaded(val navigationController: NavHostController): SplashScreenUIEvents()
}