package com.example.notes.data.login

sealed class LoginUIEvents {
    data class OnEmailChanged(val email: String) : LoginUIEvents()
    data class OnPasswordChanged(val password: String): LoginUIEvents()
    object OnLoginBtnClicked: LoginUIEvents()
}
