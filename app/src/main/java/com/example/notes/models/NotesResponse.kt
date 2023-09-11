package com.example.notes.models

data class NotesResponse(
    val v: Int,
    val id: String,
    val createdAt: String,
    val description: String,
    val title: String,
    val updatedAt: String,
    val userId: String
)