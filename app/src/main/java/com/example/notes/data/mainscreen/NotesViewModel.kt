package com.example.notes.data.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.models.NotesRequest
import com.example.notes.repository.NotesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotesViewModel @Inject constructor(private val notesRepository: NotesRepository) :
    ViewModel() {
    val _notesLiveData get() = notesRepository.notesLiveData
    val _statusLiveData get() = notesRepository.statusLiveData

    fun getNotes() {
        viewModelScope.launch {
            notesRepository.getNotes()
        }
    }

    fun insertNote(notesRequest: NotesRequest) {
        viewModelScope.launch {
            notesRepository.insertNotes(notesRequest)
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            notesRepository.deleteNotes(noteId)
        }
    }

    fun updateNote(noteId: String, notesRequest: NotesRequest) {
        viewModelScope.launch {
            notesRepository.updateNotes(noteId, notesRequest)
        }
    }
}