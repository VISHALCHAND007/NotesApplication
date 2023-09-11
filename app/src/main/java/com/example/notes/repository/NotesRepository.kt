package com.example.notes.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.R
import com.example.notes.api.NotesApi
import com.example.notes.models.NotesRequest
import com.example.notes.models.NotesResponse
import com.example.notes.utlls.NetworkResult
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val notesApi: NotesApi,
    @ApplicationContext private val context: Context
) {
    private val _notesLiveData = MutableLiveData<NetworkResult<List<NotesResponse>>>()
    val notesLiveData: LiveData<NetworkResult<List<NotesResponse>>>
        get() = _notesLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>>
        get() = _statusLiveData

    suspend fun getNotes() {
        _notesLiveData.postValue(NetworkResult.Loading())
        val response = notesApi.getNotes()
        if (response.isSuccessful && response.body() != null) {
            _notesLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _notesLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _notesLiveData.postValue(NetworkResult.Error("Something went wrong."))
        }
    }

    suspend fun insertNotes(notesRequest: NotesRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesApi.insertNote(notesRequest)
        handleResponse(response, context.getString(R.string.note_inserted))
    }

    suspend fun deleteNotes(noteId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesApi.deleteNote(noteId)
        handleResponse(response, context.getString(R.string.note_deleted))
    }

    suspend fun updateNotes(noteId: String, notesRequest: NotesRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = notesApi.updateNote(noteId, notesRequest)
        handleResponse(response, context.getString(R.string.note_updated))
    }

    private fun handleResponse(response: Response<NotesResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong."))
        }
    }
}