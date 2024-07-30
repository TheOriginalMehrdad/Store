package com.example.store.screens.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.store.model.repository.user.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    val name = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    fun  signUpUser(LoggingEvent: (String) -> Unit) {

        viewModelScope.launch {
            val result = userRepository.signUp(name.value!!, email.value!!, password.value!!)
            LoggingEvent(result)
        }

    }

}