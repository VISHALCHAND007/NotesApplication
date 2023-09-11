package com.example.notes.screens

import android.app.Activity
import android.util.Log
import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.notes.R
import com.example.notes.data.mainscreen.NoteUiEvents
import com.example.notes.data.mainscreen.NotesViewModel
import com.example.notes.uicomponents.AppSearchBar
import com.example.notes.uicomponents.EachRowComposable
import com.example.notes.uicomponents.Spacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigationController: NavHostController,
    viewModel: NotesViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val notesResponse = viewModel.noteStates.value
    val activity: Activity? = remember(context) {
        context as? Activity
    }
    val searchText = remember {
        mutableStateOf("")
    }
    viewModel.onEvent(NoteUiEvents.GetNotes)
    LaunchedEffect(key1 = viewModel.noteStates) {
//        viewModel.onEvent(NoteUiEvents.GetNotes)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = { }, containerColor = Color.DarkGray) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note",
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .background(colorResource(id = R.color.backgroundGray))
        ) {
            AppSearchBar(search = searchText.value, onValueChanged = {
                searchText.value = it
            })
            Log.e("here==233333", viewModel.isLoading.value.toString())

            if (viewModel.isLoading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (viewModel.noteStates.value.data.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(20.dp)
                ) {
                    items(notesResponse.data, key = { noteResponse ->
                        noteResponse.id
                    }) {
                        EachRowComposable(notesResponse = it)
                    }
                }
            }
        }
    }


    //to handle back button press
    BackHandler(
        enabled = true
    ) {
        activity?.finishAffinity()
    }
}