package com.example.testcompose2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModel1 @Inject constructor(): ViewModel() {

    init {
        Log.d("ViewModel1", "ViewModel1 created")
    }

    fun doSomething() {
        Log.d("ViewModel1", "ViewModel1 doSomething")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel1", "ViewModel1 cleared")
    }
}