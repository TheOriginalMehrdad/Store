package com.example.store.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.store.screens.IntroScreen
import com.example.store.screens.signIn.SignInScreen
import com.example.store.screens.signUp.SignUpScreen
import com.example.store.ui.theme.BackgroundMain
import com.example.store.ui.theme.StoreTheme
import com.example.store.utilities.KEY_CATEGORY_ARG
import com.example.store.utilities.KEY_PRODUCT_ARG
import com.example.store.utilities.MyScreens
import com.example.store.utilities.myModules
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Koin(appDeclaration = { modules(myModules) }
            ) {
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
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    StoreTheme {
        StoreTheme {
            Surface(
                color = BackgroundMain,
                modifier = Modifier.fillMaxSize()
            ) {
                MyStore(modifier = Modifier)
            }
        }
    }
}

@Composable
fun MyStore(modifier: Modifier) {

    val navController = rememberNavController()

    KoinNavHost(navController = navController, startDestination = MyScreens.IntroScreen.route) {

        composable(
            route = MyScreens.ProductScreen.route + "/" + KEY_PRODUCT_ARG,
            arguments = listOf(navArgument(KEY_PRODUCT_ARG) { type = NavType.IntType })
        ) {
            ProductScreen(it.arguments!!.getInt(KEY_PRODUCT_ARG, -1))
        }

        composable(
            route = MyScreens.CategoryScreen.route + "/" + KEY_CATEGORY_ARG,
            arguments = listOf(navArgument(KEY_CATEGORY_ARG) {
                type = NavType.StringType
            })
        ) {
            CategoryScreen(it.arguments!!.getString(KEY_CATEGORY_ARG, "null"))
        }

        composable(MyScreens.MainScreen.route) {
            MainScreen()
        }

        composable(MyScreens.ProfileScreen.route) {
            ProfileScreen()
        }

        composable(MyScreens.CartScreen.route) {
            CartScreen()
        }

        composable(MyScreens.SignUpScreen.route) {
            SignUpScreen()
        }

        composable(MyScreens.SignInScreen.route) {
            SignInScreen()
        }

        composable(MyScreens.IntroScreen.route) {
            IntroScreen()
        }

        composable(MyScreens.NoInternetScreen.route) {
            NoInternetScreen()
        }

    }


}


@Composable
fun MainScreen() {
    Surface (
        color = BackgroundMain,
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = "Hello Kotlin",
            modifier = Modifier.wrapContentSize(),
            fontSize = 33.sp
        )
    }
}

@Composable
fun NoInternetScreen() {
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




