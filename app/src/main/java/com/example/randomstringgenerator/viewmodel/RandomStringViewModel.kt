package com.example.randomstringgenerator.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomstringgenerator.data.model.RandomText
import com.example.randomstringgenerator.data.repository.RandomStringRepository
import kotlinx.coroutines.launch

class RandomStringViewModel(context: Context) : ViewModel() {
    private var _randomStringRepository = RandomStringRepository(context = context)
    private var _randomStringList = MutableLiveData<List<RandomText>>()
    val randomStringList: LiveData<List<RandomText>> = _randomStringList

    init {
        getAllRandomString()
    }

    fun getAllRandomString() {
        _randomStringList.value = _randomStringRepository.getAllRandomGeneratedStrings().reversed()
    }

    fun addRandomString(length: Int) {
        viewModelScope.launch {
            _randomStringRepository.addRandomString(length)
            getAllRandomString()
        }
    }

    fun deleteRandomString(randomText: RandomText) {
        _randomStringRepository.deleteRandomString(randomText)
        getAllRandomString()
    }

    fun deleteAllRandomString() {
        _randomStringRepository.deleteAllRandomString()
        getAllRandomString()
    }
}