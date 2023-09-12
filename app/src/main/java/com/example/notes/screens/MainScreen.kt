package com.example.notes.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notes.R
import com.example.notes.data.mainscreen.NoteUiEvents
import com.example.notes.data.mainscreen.NotesViewModel
import com.example.notes.models.NotesRequest
import com.example.notes.uicomponents.AppSearchBar
import com.example.notes.uicomponents.EachRowComposable
import com.example.notes.uicomponents.ShowDialogBox
import com.example.notes.uicomponents.Spacer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
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
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var isAddDialog by remember {
        mutableStateOf(false)
    }
    var needToUpdate by remember {
        mutableStateOf(false)
    }
    var noteId by remember {
        mutableStateOf("")
    }
    viewModel.onEvent(NoteUiEvents.GetNotes)
    LaunchedEffect(key1 = viewModel.noteStates) {
        viewModel.onEvent(NoteUiEvents.GetNotes)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                isAddDialog = true
            }, containerColor = Color.DarkGray) {
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
            if (viewModel.isLoading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            if (viewModel.noteStates.value.data.isNotEmpty()) {
                Spacer()
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalItemSpacing = 15.dp,
                    contentPadding = PaddingValues(15.dp)
                ) {
                    items(notesResponse.data.size) { it ->
                        EachRowComposable(
                            notesResponse = notesResponse.data[it],
                            onDeleteBtnClicked = { noteId ->
                                viewModel.onEvent(NoteUiEvents.DeleteNote(noteId))
                                viewModel.onEvent(NoteUiEvents.GetNotes)
                            },
                            onNoteClicked = {notesResponse ->
                                //Update Note
                                title = notesResponse.title
                                description = notesResponse.description
                                isAddDialog = true
                                needToUpdate = true
                                noteId = notesResponse._id
                            }
                        )
                    }
                }
            }
            if (isAddDialog) {
                ShowDialogBox(
                    title = title,
                    description = description,
                    onTitleChanged = {
                        title = it
                    },
                    onDescriptionChanged = {
                        description = it
                    },
                    onButtonClicked = {
                       if(needToUpdate) {
                            viewModel.onEvent(NoteUiEvents.UpdateNote(noteId, NotesRequest(
                                title = title,
                                description = description
                            )
                            ))
                           noteId = ""
                       } else {
                           //Save note
                           viewModel.onEvent(
                               NoteUiEvents.InsertNote(
                                   NotesRequest(
                                       title = title,
                                       description = description
                                   )
                               )
                           )
                       }
                        isAddDialog = false
                        needToUpdate = false
                        title = ""
                        description = ""
                        viewModel.onEvent(NoteUiEvents.GetNotes)
                    },
                    onClose = {
                        isAddDialog = false
                        needToUpdate = false
                        title = ""
                        description = ""
                    },
                    isSaveBtnEnabled =
                    title.isNotBlank() && description.isNotBlank()
                )
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