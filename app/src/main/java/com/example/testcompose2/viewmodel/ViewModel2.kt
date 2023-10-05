package com.example.testcompose2.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class A() {

        init {
            Log.d("A", "A created")
        }

        fun doSomething() {
            Log.d("A", "A doSomething")
        }
}
class ViewModel2: ViewModel() {

    init {
        Log.d("ViewModel2", "ViewModel2 created")
    }

    fun doSomething() {
        Log.d("ViewModel2", "ViewModel2 doSomething")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModel2", "ViewModel2 cleared")
    }
}