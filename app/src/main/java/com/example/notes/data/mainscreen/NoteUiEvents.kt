package com.example.notes.data.mainscreen

import com.example.notes.models.NotesRequest
import com.example.notes.models.NotesResponse

sealed class NoteUiEvents {
    object GetNotes: NoteUiEvents()
    data class InsertNote(val notesRequest: NotesRequest): NoteUiEvents()
    data class UpdateNote(val noteId: String, val notesRequest: NotesRequest): NoteUiEvents()
    data class DeleteNote(val noteId: String): NoteUiEvents()
}
