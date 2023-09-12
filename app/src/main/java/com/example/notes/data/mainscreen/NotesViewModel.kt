package com.example.notes.data.mainscreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.models.NotesResponse
import com.example.notes.repository.NotesRepository
import com.example.notes.utlls.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository) :
    ViewModel() {
    private val _notesLiveData get() = notesRepository.notesLiveData
    private val _statusLiveData get() = notesRepository.statusLiveData
    val noteStates = mutableStateOf(NoteStates())
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    fun onEvent(event: NoteUiEvents) {
        when (event) {
            is NoteUiEvents.DeleteNote -> {
                viewModelScope.launch {
                    notesRepository.deleteNotes(event.noteId)
                    isLoading.value = false
                    _statusLiveData.observeForever {
                        handleResponse(it)
                    }
                }
            }

            is NoteUiEvents.GetNotes -> {
                viewModelScope.launch {
                    notesRepository.getNotes()
                    _notesLiveData.observeForever { networkResult ->
                        isLoading.value = false
                        when (networkResult) {
                            is NetworkResult.Error -> {
                                errorMessage.value = networkResult.message!!
                            }
                            is NetworkResult.Loading -> {
                                isLoading.value = true
                            }

                            is NetworkResult.Success -> {
                                Log.d("here123", networkResult.data.toString())
                                noteStates.value.data = networkResult.data ?: emptyList()
                            }
                        }
                    }
                }
            }

            is NoteUiEvents.InsertNote -> {
                viewModelScope.launch {
                    notesRepository.insertNotes(event.notesRequest)
                    isLoading.value = false
                    _statusLiveData.observeForever { networkResult ->
                        handleResponse(networkResult)
                    }
                }
            }

            is NoteUiEvents.UpdateNote -> {
                viewModelScope.launch {
                    isLoading.value = false
                    notesRepository.updateNotes(event.noteId, event.notesRequest)
                    _statusLiveData.observeForever {
                        handleResponse(it)
                    }
                }
            }
        }
    }

    private fun handleResponse(networkResult: NetworkResult<String>) {
        isLoading.value = false
        when (networkResult) {
            is NetworkResult.Error -> {
                errorMessage.value = networkResult.message!!
            }

            is NetworkResult.Loading -> {
                isLoading.value = true
            }

            is NetworkResult.Success -> {
//                Log.d(TAG, networkResult.message.toString())
            }
        }
    }
}