package com.conamobile.r8appsizecompiler.network

import com.conamobile.r8appsizecompiler.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}