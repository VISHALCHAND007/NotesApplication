package com.example.notes.data.login

import androidx.navigation.NavHostController

sealed class LoginUIEvents {
    data class OnEmailChanged(val email: String) : LoginUIEvents()
    data class OnPasswordChanged(val password: String): LoginUIEvents()
    data class OnLoginBtnClicked(val navHostController: NavHostController): LoginUIEvents()
}
