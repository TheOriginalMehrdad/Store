package com.example.store.model.repository.user

import android.content.SharedPreferences
import com.example.store.net.ApiService
import com.example.store.utilities.VALUE_SUCCESS
import com.google.gson.JsonObject

class UserRepositoryImpl(
    val apiService: ApiService,
    val sharedPreferences: SharedPreferences
) : UserRepository {

    override suspend fun signUp(name: String, userName: String, password: String): String {

        val jsonObject = JsonObject().apply {
            addProperty("name", name)
            addProperty("userName", userName)
            addProperty("password", password)
        }
        val result = apiService.signUp(jsonObject)

        if (result.success) {

            TokenInMemory.refreshToken(userName, result.token)
            saveToken(result.token)
            saveUserName(userName)

            return VALUE_SUCCESS

        } else {
            return result.message
        }
    }

    override suspend fun signIn(userName: String, password: String): String {

        val jsonObject = JsonObject().apply {
            addProperty("email", userName)
            addProperty("password", password)
        }
        val result = apiService.signIn(jsonObject)

        if (result.success) {

            TokenInMemory.refreshToken(userName, result.token)
            saveToken(result.token)
            saveUserName(userName)

            return VALUE_SUCCESS

        } else {
            return result.message
        }
    }

    override fun singOut() {

        TokenInMemory.refreshToken(null, null)
        sharedPreferences.edit().clear().apply()
    }

    override fun loadToken() {
        TokenInMemory.refreshToken(getUserName(), getToken())
    }

    override fun saveToken(newToken: String) {
        sharedPreferences.edit().putString("token", newToken).apply()
    }

    override fun getToken(): String {
        return sharedPreferences.getString("token", "")!!
    }

    override fun saveUserName(userName: String) {
        sharedPreferences.edit().putString("userName", userName).apply()
    }

    override fun getUserName(): String {
        return sharedPreferences.getString("userName ", "")!!
    }

}