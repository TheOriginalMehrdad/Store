package com.example.store.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import  androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.store.R
import com.example.store.ui.theme.BackgroundMain
import com.example.store.ui.theme.Blue
import com.example.store.ui.theme.StoreTheme
import com.example.store.utilities.MyScreens
import dev.burnoo.cokoin.navigation.getNavController

@Preview(showBackground = true)
@Composable
fun IntroScreenPreview() {
    StoreTheme {
        Surface(
            color = BackgroundMain,
            modifier = Modifier.fillMaxSize()
        ) {
            IntroScreen()
        }
    }
}

@Composable
fun IntroScreen() {

//    val systemUiController = rememberSystemUiController()
//    systemUiController.setStatusBarColor(Blue)

    val navigationController = getNavController()

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.img_intro),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,

        ) {

        Button(
            onClick = {
                navigationController.navigate(MyScreens.SignUpScreen.route)
            },
            modifier = Modifier.fillMaxWidth(0.7f),

            ) {
            Text(text = "Sign Up")
        }
        Button(
            onClick = {
                navigationController.navigate(MyScreens.SignInScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 9.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(text = "Sign In", color = Blue)
        }

    }
}