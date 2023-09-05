package com.example.notes.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.notes.utlls.Constants.TAG

@Composable
fun RegistrationScreen(navigationController: NavHostController) {
    var scrollState = rememberScrollState()

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
        MyTextField(onValueChanged = {})
        Spacer()
        HeadingText(text = stringResource(id = R.string.email))
        Spacer()
        MyTextField(onValueChanged = {})
        Spacer()
        HeadingText(text = stringResource(id = R.string.passwordA))
        Spacer()
        PasswordTextField(onValueChanged = {})
        Spacer(value = 50.dp)
        ButtonComp(text = stringResource(id = R.string.sign_up)) {

        }
        Spacer(value = 20.dp)
        AnnotatedText(
            normalText = stringResource(id = R.string.already_have_account),
            hyperText = stringResource(id = R.string.login),
            onHyperTextClicked = {
                Log.e("here==", it)
                if (it == "Login") {
                    navigationController.navigate(Screen.LoginScreen.route)
                }
            })
    }
}

