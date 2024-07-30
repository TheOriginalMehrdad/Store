package com.example.store.di

import android.content.Context
import com.example.store.model.repository.user.UserRepository
import com.example.store.net.createApiService
import com.example.store.screens.signIn.SignInViewModel
import com.example.store.screens.signUp.SignUpViewModel
import ir.dunijet.dunibazaar.model.repository.user.UserRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val myModules = module {
    single { androidContext().getSharedPreferences("data", Context.MODE_PRIVATE) }
    single { createApiService() }
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
}