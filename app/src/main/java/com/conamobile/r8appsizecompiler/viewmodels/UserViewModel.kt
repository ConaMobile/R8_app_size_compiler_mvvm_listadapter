package com.conamobile.r8appsizecompiler.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.conamobile.r8appsizecompiler.model.User
import com.conamobile.r8appsizecompiler.repositories.UserRepository
import com.conamobile.r8appsizecompiler.utils.NetworkHelper
import com.conamobile.r8appsizecompiler.utils.Resource
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
    private val networkHelper: NetworkHelper,
    private val context: Context,
) : ViewModel() {

    private val liveData = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            liveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val remoteUsers = userRepository.getAllUsers()
                if (remoteUsers.isSuccessful) {
                    liveData.postValue(Resource.success(remoteUsers.body()))
                }
            } else {
                Toast.makeText(context, "Ma'lumotni olib kelib bo'lmadi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>> = liveData
}