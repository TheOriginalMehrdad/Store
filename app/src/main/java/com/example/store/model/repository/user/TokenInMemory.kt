package com.example.store.model.repository.user

object TokenInMemory {

    var userName: String? = null
        private set
    var token: String? = null
        private set

    fun refreshToken(userName: String?, newToken: String?) {

        this.userName = userName
        this.token = newToken
    }

}