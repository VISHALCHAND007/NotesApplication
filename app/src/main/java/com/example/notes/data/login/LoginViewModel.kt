package com.example.notes.data.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.rules.Validator
import com.example.notes.models.UserRequest
import com.example.notes.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val loginUIState = mutableStateOf(LoginUIState())
    val allValidationChecked = mutableStateOf(false)

    fun onEvent(event: LoginUIEvents) {
        when(event) {
            is LoginUIEvents.OnEmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }
            is LoginUIEvents.OnPasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }
            is LoginUIEvents.OnLoginBtnClicked -> {
                loginUser(
                    UserRequest(
                        email = loginUIState.value.email,
                        password = loginUIState.value.password,
                        username = ""
                    )
                )
            }
        }
    }
    private fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }
    private fun validateDataWithRules() {
        val email = Validator.validateEmail(loginUIState.value.email)
        val password = Validator.validatePassword(loginUIState.value.password)

        loginUIState.value = loginUIState.value.copy(
            emailError = email.result,
            passwordError = password.result
        )
        allValidationChecked.value = email.result && password.result
    }
}