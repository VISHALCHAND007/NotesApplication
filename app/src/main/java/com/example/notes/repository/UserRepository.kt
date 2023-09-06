package com.example.notes.repository

import android.util.Log
import com.example.notes.api.UserApi
import com.example.notes.models.UserRequest
import com.example.notes.utlls.Constants.TAG
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {
    suspend fun registerUser(userRequest: UserRequest) {
        val response = userApi.signUp(userRequest)
        Log.d(TAG, response.body().toString())
    }

    suspend fun loginUser(userRequest: UserRequest) {
        val response = userApi.signIn(userRequest)
        Log.d(TAG, response.body().toString())
    }
}