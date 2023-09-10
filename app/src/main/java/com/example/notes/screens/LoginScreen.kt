package com.example.notes.screens

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.notes.R
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
fun LoginScreen(navigationController: NavHostController) {
    var scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp, end = 15.dp, start = 15.dp)
            .verticalScroll(state = scrollState, enabled = true)
    ) {
        BoldText(text = stringResource(id = R.string.welcome))
        NormalText(text = stringResource(id = R.string.login_to_access))
        Spacer(value = 50.dp)
        HeadingText(text = stringResource(id = R.string.email))
        Spacer()
        MyTextField(onValueChanged = {})
        Spacer()
        HeadingText(text = stringResource(id = R.string.passwordA))
        Spacer()
        PasswordTextField(onValueChanged = {})
        Spacer(value = 50.dp)
        ButtonComp(text = stringResource(id = R.string.login),
            onButtonClicked = {
                navigationController.navigate(Screen.MainScreen.route)

            })
        Spacer(value = 20.dp)
        AnnotatedText(
            normalText = stringResource(id = R.string.dont_have_an_account),
            hyperText = stringResource(id = R.string.register),
            onHyperTextClicked = {
                if (it.lowercase().contains("register")) {
                    navigationController.navigate(Screen.RegistrationScreen.route)
                }
            })
    }
}