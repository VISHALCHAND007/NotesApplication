package com.example.notes.api

import com.example.notes.models.UserRequest
import com.example.notes.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("user/signin")
    suspend fun signIn(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("user/signup")
    suspend fun signUp(@Body userRequest: UserRequest) : Response<UserResponse>
}