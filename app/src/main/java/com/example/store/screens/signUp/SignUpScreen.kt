package com.example.store.screens.signUp


import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.store.R
import com.example.store.ui.theme.BackgroundMain
import com.example.store.ui.theme.Blue
import com.example.store.ui.theme.StoreTheme
import com.example.store.ui.theme.myShapes
import com.example.store.ui.theme.textHeaderStyleRegular
import com.example.store.ui.theme.textHelperStyleSmall
import com.example.store.utilities.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {

    StoreTheme {
        Surface(
            color = BackgroundMain,
            modifier = Modifier.wrapContentSize()
        ) {
            SignUpScreen()
        }
    }
}


@Composable
fun SignUpScreen() {

    val myViewModel = getNavViewModel<SignUpViewModel>()
    val navigation = getNavController()

    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f)
                .background(Blue)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconTopic()
            MainCardView(navigation, myViewModel) {
                myViewModel.signUpUser()
            }
        }
    }
}


@Composable
fun MainTextField(
    edtValue: String,
    icon: Int,
    hint: String,
    onValueChangeFunction: (String) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        onValueChange = onValueChangeFunction,
        placeholder = {
            if (edtValue.isEmpty() && !isFocused) {
                Text(hint)
            }
        },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 4.dp, bottom = 4.dp),
        shape = myShapes.medium,
        leadingIcon = { Icon(painterResource(icon), null) },
        singleLine = true,
        interactionSource = interactionSource
    )
}


@Composable
fun MainCardView(navigation: NavController, viewModel: SignUpViewModel, signUpEvent: () -> Unit) {


    val name = viewModel.name.observeAsState("")
    val email = viewModel.email.observeAsState("")
    val password = viewModel.password.observeAsState("")
    val confirmPassword = viewModel.confirmPassword.observeAsState("")

    val context = LocalContext.current


    Card(
        colors = CardDefaults.cardColors(BackgroundMain),
        modifier = Modifier
            .wrapContentSize()
            .padding(18.dp),
        shape = myShapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign Up",
                color = Blue,
                style = textHeaderStyleRegular,
                modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
            )

            MainTextField(
                edtValue = name.value, icon = R.drawable.ic_person, hint = "Full name"
            ) { viewModel.name.value = it }

            MainTextField(
                edtValue = email.value, icon = R.drawable.ic_email, hint = "Email Address"
            ) { viewModel.email.value = it }

            PasswordTextField(
                edtValue = password.value, icon = R.drawable.ic_password, hint = "Password"
            ) { viewModel.password.value = it }

            PasswordTextField(
                edtValue = confirmPassword.value,
                icon = R.drawable.ic_password,
                hint = "Confirm Password",
            ) { viewModel.confirmPassword.value = it }

            Button(
                onClick = {
                    if (name.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty() && confirmPassword.value.isNotEmpty()) {

                        if (password.value == confirmPassword.value) {

                            if (password.value.length >= 8) {

                                if (Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
                                    signUpEvent.invoke()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "The Email format is invalid",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            } else {
                                Toast.makeText(
                                    context,
                                    "The password must be at least 8 characters",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        } else {
                            Toast.makeText(context, "Passwords doesn't match!", Toast.LENGTH_SHORT)
                                .show()
                        }

                    } else {
                        Toast.makeText(context, "Please enter all data first!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 27.dp),
                shape = myShapes.large
            ) {
                Text(text = "Register Account")
            }

            Row(
                modifier = Modifier.padding(top = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account ?",
                    style = textHelperStyleSmall,
                )
                TextButton(
                    onClick = {
                        navigation.navigate(MyScreens.SignInScreen.route) {
                            popUpTo(MyScreens.SignUpScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text(text = "Sign In", color = Blue, style = textHelperStyleSmall)
                }
            }
        }

    }
}


@Composable
fun IconTopic() {
    Surface(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_icon_app),
            contentDescription = null,
            modifier = Modifier.padding(13.dp)
        )
    }
}


@Composable
fun PasswordTextField(
    edtValue: String,
    icon: Int,
    hint: String,
    onValueChangeFunction: (String) -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        onValueChange = onValueChangeFunction,
        placeholder = {
            if (edtValue.isEmpty() && !isFocused) {
                Text(hint)
            }
        },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 4.dp, bottom = 4.dp),
        shape = myShapes.medium,
        leadingIcon = { Icon(painterResource(icon), contentDescription = null) },
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    painter = painterResource(
                        id = if (passwordVisibility) R.drawable.ic_invisible else R.drawable.ic_visible
                    ),
                    contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                )
            }
        },
        interactionSource = interactionSource
    )
}

