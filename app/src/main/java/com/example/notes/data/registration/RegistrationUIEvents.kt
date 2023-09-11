package com.example.notes.data.registration

import androidx.navigation.NavHostController

sealed class RegistrationUIEvents {
    data class OnUsernameChanged(val username: String) : RegistrationUIEvents()
    data class OnPasswordChanged(val password: String) : RegistrationUIEvents()
    data class OnEmailChanged(val email: String) : RegistrationUIEvents()
    data class CheckUserLogin(val navigationController: NavHostController) : RegistrationUIEvents()
    data class OnSignUpBtnClicked(val navigationController: NavHostController) : RegistrationUIEvents()
}
