package com.conamobile.r8appsizecompiler.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.conamobile.r8appsizecompiler.repositories.UserRepository
import com.conamobile.r8appsizecompiler.utils.NetworkHelper

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val networkHelper: NetworkHelper,
    private val context: Context,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository, networkHelper, context) as T
        }
        throw IllegalArgumentException("Error")
    }
}