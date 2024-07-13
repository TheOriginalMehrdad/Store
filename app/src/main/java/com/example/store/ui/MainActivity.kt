package com.example.store.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.store.ui.theme.StoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StoreTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyStore(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    StoreTheme {
        MyStore(modifier = Modifier)
    }
}

@Composable
fun MyStore(modifier: Modifier) {

    Surface(
        color = Color.White,
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = "Hello Jetpack Compose",
            modifier = Modifier.padding(horizontal = 9.dp)
        )
    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "") {

        composable("mainScreen") {
            MainScreen()
        }

        composable(
            route = "productScreen/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            ProductScreen(it.arguments!!.getInt("productId", -1))
        }

        composable(route = "categoryScreen", arguments = listOf(navArgument("categoryName") {
            type = NavType.StringType
        })) {
            CategoryScreen(it.arguments!!.getString("categoryName", "null"))
        }

        composable("profileScreen") {
            ProfileScreen()
        }

        composable("cartScreen") {
            CartScreen()
        }

        composable("signUpScreen") {
            SignUpScreen()
        }

        composable("signInScreen") {
            SignInScreen()
        }

        composable("introScreen") {
            IntroScreen()
        }

        composable("noInternetScreen") {
            NoInternetScreen()
        }

    }
}

@Composable
fun IntroScreen() {
    TODO("Not yet implemented")
}

@Composable
fun MainScreen() {

}

@Composable
fun NoInternetScreen() {
    TODO("Not yet implemented")
}

@Composable
fun SignInScreen() {
    TODO("Not yet implemented")
}

@Composable
fun SignUpScreen() {
    TODO("Not yet implemented")
}

@Composable
fun CartScreen() {
    TODO("Not yet implemented")
}

@Composable
fun ProfileScreen() {
    TODO("Not yet implemented")
}

@Composable
fun CategoryScreen(categoryName: String) {
    TODO("Not yet implemented")
}

@Composable
fun ProductScreen(productId: Int) {
    TODO("Not yet implemented")
}




