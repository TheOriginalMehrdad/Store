package com.example.store.screens.signIn


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.store.model.repository.user.UserRepository

class SignInViewModel(private val userRepository: UserRepository) : ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    fun signInUser() {
       // Log.v("check", "signUpUser " + name.value)
    }
}