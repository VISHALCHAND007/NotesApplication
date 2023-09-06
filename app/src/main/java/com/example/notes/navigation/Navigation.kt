package com.example.notes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.isPopupLayout
import androidx.navigation.NavHostController
import androidx.navigation.PopUpToBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.screens.LoginScreen
import com.example.notes.screens.MainScreen
import com.example.notes.screens.RegistrationScreen

@Composable
fun Navigation() {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Screen.RegistrationScreen.route) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navigationController)

        }
        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(navigationController)
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(navigationController)
        }
    }
}
