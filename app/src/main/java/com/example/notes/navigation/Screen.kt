package com.example.notes.navigation

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login")
    object RegistrationScreen: Screen("registration")
    object MainScreen: Screen("main")
    object SplashScreen: Screen("splash")

    fun args(vararg arg: String): String {
        return buildString {
            append(route)
            arg.forEach {
                append("/$it")
            }
        }
    }
}
