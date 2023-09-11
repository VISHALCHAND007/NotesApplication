package com.example.notes.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navigationController: NavHostController) {

    val context = LocalContext.current
    val activity : Activity? = remember(context) {
        context as? Activity
    }
    BackHandler(
        enabled = true
    ) {
        activity?.finishAffinity()
    }
}