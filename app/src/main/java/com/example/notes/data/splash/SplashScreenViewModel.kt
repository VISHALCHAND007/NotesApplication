package com.example.notes.data.splash

import androidx.lifecycle.ViewModel
import com.example.notes.navigation.Screen
import com.example.notes.utlls.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var tokenManager: TokenManager

    fun onEvent(event: SplashScreenUIEvents) {
        when(event) {
            is SplashScreenUIEvents.OnLoaded -> {
                val token = tokenManager.getToken()
                if(token.isNullOrBlank()) {
                    event.navigationController.navigate(Screen.RegistrationScreen.route)
                } else {
                    event.navigationController.navigate(Screen.MainScreen.route)
                }
            }
        }
    }
}