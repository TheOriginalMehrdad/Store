package com.example.store.screens


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.store.ui.theme.BackgroundMain
import com.example.store.ui.theme.StoreTheme

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    StoreTheme {
        Surface(
            color = BackgroundMain,
            modifier = Modifier.fillMaxSize()
        ) {
            SignInScreen()
        }
    }
}

@Composable
fun SignInScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundMain
    ){
        Text(
            text = "SignIn Screen",
            modifier = Modifier.wrapContentSize(),
            fontSize = 33.sp
        )

    }
}