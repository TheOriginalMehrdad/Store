package com.example.store.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

@SuppressLint("StaticFieldLeak")

object ToastHelper {
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    fun makeToast(name: String) = Toast.makeText(context, name, Toast.LENGTH_SHORT).show()

}