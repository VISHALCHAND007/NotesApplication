package com.example.notes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.notes.R
import com.example.notes.data.registration.RegistrationUIEvents
import com.example.notes.data.registration.RegistrationViewModel
import com.example.notes.navigation.Screen
import com.example.notes.uicomponents.AnnotatedText
import com.example.notes.uicomponents.BoldText
import com.example.notes.uicomponents.ButtonComp
import com.example.notes.uicomponents.HeadingText
import com.example.notes.uicomponents.MyTextField
import com.example.notes.uicomponents.NormalText
import com.example.notes.uicomponents.PasswordTextField
import com.example.notes.uicomponents.Spacer

@Composable
fun RegistrationScreen(
    navigationController: NavHostController,
    registrationViewModel: RegistrationViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp, end = 15.dp, start = 15.dp)
            .verticalScroll(state = scrollState, enabled = true)
    ) {
        BoldText(text = stringResource(id = R.string.create_account))
        NormalText(text = stringResource(id = R.string.provide_info))
        Spacer(value = 50.dp)
        HeadingText(text = stringResource(id = R.string.username))
        Spacer()
        MyTextField(
            onValueChanged = {
                registrationViewModel.onEvent(event = RegistrationUIEvents.OnUsernameChanged(it))
            },
            errorStatus = registrationViewModel.registrationUIState.value.usernameError
        )
        Spacer()
        HeadingText(text = stringResource(id = R.string.email))
        Spacer()
        MyTextField(
            onValueChanged = {
                registrationViewModel.onEvent(event = RegistrationUIEvents.OnEmailChanged(it))
            },
            errorStatus = registrationViewModel.registrationUIState.value.emailError
        )
        Spacer()
        HeadingText(text = stringResource(id = R.string.passwordA))
        Spacer()
        PasswordTextField(
            onValueChanged = {
                registrationViewModel.onEvent(event = RegistrationUIEvents.OnPasswordChanged(it))
            },
            errorStatus = registrationViewModel.registrationUIState.value.passwordError
        )
        Spacer(value = 50.dp)
        ButtonComp(text = stringResource(id = R.string.sign_up)) {
            registrationViewModel.onEvent(event = RegistrationUIEvents.OnSignUpBtnClicked)
        }
        Spacer(value = 20.dp)
        AnnotatedText(
            normalText = stringResource(id = R.string.already_have_account),
            hyperText = stringResource(id = R.string.login),
            onHyperTextClicked = {
                if (it == "Login") {
                    navigationController.navigate(Screen.LoginScreen.route)
                }
            }
        )
    }
}

