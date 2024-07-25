package com.example.store.model.repository.user

interface UserRepository {

    // Online stuff
    suspend fun signUp(name: String, userName: String, password: String):String
    suspend fun signIn(userName: String, password: String):String


    // Offline stuff
    fun singOut()

    fun loadToken()
    fun saveToken(newToken: String)
    fun getToken(): String

    fun saveUserName(userName: String)
    fun getUserName(): String
}