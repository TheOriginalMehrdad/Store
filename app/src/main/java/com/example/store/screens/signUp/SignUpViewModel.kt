package com.example.store.screens.signUp


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.store.model.repository.user.UserRepository

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    var name = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    fun signUpUser() {
        Log.v("check", "signUpUser " + name.value)
    }
}