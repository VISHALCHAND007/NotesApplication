package com.example.notes.data.registration

sealed class RegistrationUIEvents {
    data class OnUsernameChanged(val username: String): RegistrationUIEvents()
    data class OnPasswordChanged(val password: String): RegistrationUIEvents()
    data class OnEmailChanged(val email: String): RegistrationUIEvents()
    object OnSignUpBtnClicked: RegistrationUIEvents()
}
