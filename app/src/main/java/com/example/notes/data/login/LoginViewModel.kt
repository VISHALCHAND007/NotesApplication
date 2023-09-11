package com.example.notes.data.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
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
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    val loginUIState = mutableStateOf(LoginUIState())
    val allValidationChecked = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")
    @Inject
    lateinit var tokenManager: TokenManager

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    fun onEvent(event: LoginUIEvents) {
        validateDataWithRules()
        when (event) {
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
                    ),
                    navController = event.navHostController
                )
            }
        }
    }

    private fun loginUser(userRequest: UserRequest, navController: NavHostController) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
        userResponseLiveData.observeForever {networkResult ->
            isLoading.value = false
            when(networkResult) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(networkResult.data!!.token)
                    navController.navigate(Screen.MainScreen.route)
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
        val email = Validator.validateEmail(loginUIState.value.email)
        val password = Validator.validatePassword(loginUIState.value.password)

        loginUIState.value = loginUIState.value.copy(
            emailError = email.result,
            passwordError = password.result
        )
        allValidationChecked.value = email.result && password.result
    }
}