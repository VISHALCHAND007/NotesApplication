package com.example.notes.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notes.data.login.LoginViewModel
import com.example.notes.data.registration.RegistrationViewModel
import com.example.notes.screens.LoginScreen
import com.example.notes.screens.MainScreen
import com.example.notes.screens.RegistrationScreen

@Composable
fun Navigation() {
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = Screen.RegistrationScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                navigationController,
                loginViewModel
            )
        }
        composable(route = Screen.RegistrationScreen.route) {
            val registrationViewModel = hiltViewModel<RegistrationViewModel>()
            RegistrationScreen(
                navigationController,
                registrationViewModel
            )
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(navigationController)
        }
    }
}
