package ir.dunijet.dunibazaar.ui.features.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ir.dunijet.dunibazaar.model.repository.user.UserRepository

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val email = mutableStateOf("")
    val address = mutableStateOf("")
    val postalCode = mutableStateOf("")
    val loginTime = mutableStateOf("")
    val showLocationDialog = mutableStateOf(false)

    fun loadUserData() {

        val location = userRepository.getUserLocation()

        address.value = location.first
        postalCode.value = location.second
        email.value = userRepository.getUserName()!!
        loginTime.value = userRepository.getUserLoginTime()
    }

    fun signOut() {
        userRepository.signOut()
    }

    fun setUserLocation(address: String, postalCode: String) {
        userRepository.saveUserLocation(address, postalCode)
    }
}