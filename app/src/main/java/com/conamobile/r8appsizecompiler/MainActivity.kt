package com.conamobile.r8appsizecompiler

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.conamobile.r8appsizecompiler.adapters.UserAdapter
import com.conamobile.r8appsizecompiler.databinding.ActivityMainBinding
import com.conamobile.r8appsizecompiler.network.ApiClient
import com.conamobile.r8appsizecompiler.repositories.UserRepository
import com.conamobile.r8appsizecompiler.utils.NetworkHelper
import com.conamobile.r8appsizecompiler.utils.Status
import com.conamobile.r8appsizecompiler.viewmodels.UserViewModel
import com.conamobile.r8appsizecompiler.viewmodels.ViewModelFactory

//task1 -> minifyEnabled true
//task2 -> data class add @Keep
//task3 -> add debug minifyEnabled true
// finally app size 2.4 mb
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        setupViewModel()
        setupAdapter()
        loadItems()
        listAdapterClick()
    }

    private fun listAdapterClick() {
        userAdapter.itemCLick = {
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadItems() {
        binding.progress.visibility = View.VISIBLE
        userViewModel.getUsers().observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.rv.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    userAdapter.submitList(it.data)
                    binding.progress.visibility = View.GONE
                    binding.rv.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupAdapter() {
        userAdapter = UserAdapter()
        binding.rv.adapter = userAdapter
    }

    private fun setupViewModel() {
        val userRepository = UserRepository(ApiClient.apiService)
        val networkHelper = NetworkHelper(this)
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(userRepository, networkHelper, this)
        )[UserViewModel::class.java]
    }
}