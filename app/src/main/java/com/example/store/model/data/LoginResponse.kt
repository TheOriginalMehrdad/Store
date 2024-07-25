package com.example.store.model.data

data class LoginResponse(

    val expireAt: Int,
    val message: String,
    val success: Boolean,
    val token: String
)
