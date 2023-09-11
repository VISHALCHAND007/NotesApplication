package com.example.notes.di

import com.example.notes.api.AuthInterceptor
import com.example.notes.api.NotesApi
import com.example.notes.api.UserApi
import com.example.notes.utlls.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofitBuilder(): Builder {
        return Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(authInterceptor).build()
    }

    @Provides
    @Singleton
    fun providesUserAPI(retrofitBuilder: Builder): UserApi {
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun providesNotesAPI(retrofitBuilder: Builder, okHttpClient: OkHttpClient): NotesApi {
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(NotesApi::class.java)
    }
}