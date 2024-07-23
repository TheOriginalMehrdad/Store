package com.example.store.screens.signIn


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    fun signInUser() {
       // Log.v("check", "signUpUser " + name.value)
    }
}