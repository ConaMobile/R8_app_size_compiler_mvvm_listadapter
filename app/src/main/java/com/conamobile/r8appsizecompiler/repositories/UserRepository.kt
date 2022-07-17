package com.conamobile.r8appsizecompiler.repositories

import com.conamobile.r8appsizecompiler.network.ApiService

class UserRepository(
    private val apiService: ApiService,
) {
    suspend fun getAllUsers() = apiService.getUsers()
}