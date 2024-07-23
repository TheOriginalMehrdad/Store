package com.example.store.utilities

import com.example.store.screens.signIn.SignInViewModel
import com.example.store.screens.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    viewModel { SignUpViewModel() }
    viewModel { SignInViewModel() }
}