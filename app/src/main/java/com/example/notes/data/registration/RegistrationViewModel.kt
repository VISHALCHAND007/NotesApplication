package com.example.notes.data.registration

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.notes.data.rules.Validator
import com.example.notes.models.UserRequest
import com.example.notes.models.UserResponse
import com.example.notes.navigation.Screen
import com.example.notes.repository.UserRepository
import com.example.notes.utlls.NetworkResult
import com.example.notes.utlls.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    val registrationUIState = mutableStateOf(RegistrationUIState())
    val allValidationsChecked = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    private val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData
    @Inject
        lateinit var tokenManager: TokenManager

    fun onEvent(event: RegistrationUIEvents) {
        validateDataWithRules()
        when (event) {
            is RegistrationUIEvents.OnUsernameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    username = event.username
                )
            }

            is RegistrationUIEvents.OnEmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }

            is RegistrationUIEvents.OnPasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }

            is RegistrationUIEvents.OnSignUpBtnClicked -> {
                registerUser(
                    UserRequest(
                        username = registrationUIState.value.username,
                        email = registrationUIState.value.email,
                        password = registrationUIState.value.password
                    ),
                    event.navigationController
                )
            }
        }
    }

    private fun registerUser(userRequest: UserRequest, navigationController: NavHostController) {
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
        userResponseLiveData.observeForever { networkResult ->
            isLoading.value  = false
            when (networkResult) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(networkResult.data!!.token)
                    navigationController.navigate(Screen.MainScreen.route)
                }
                is NetworkResult.Error -> {
                    errorMessage.value = networkResult.message!!
                }
                is NetworkResult.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }

    private fun validateDataWithRules() {
        val username = Validator.validateUsername(registrationUIState.value.username)
        val email = Validator.validateEmail(registrationUIState.value.email)
        val password = Validator.validatePassword(registrationUIState.value.password)

        registrationUIState.value = registrationUIState.value.copy(
            usernameError = username.result,
            passwordError = password.result,
            emailError = email.result
        )
        allValidationsChecked.value = username.result && email.result && password.result
    }
}