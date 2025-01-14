package com.example.randomstringgenerator.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RandomStringViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RandomStringViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RandomStringViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}