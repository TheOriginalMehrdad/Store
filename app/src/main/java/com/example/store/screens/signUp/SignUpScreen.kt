package com.example.store.screens.signUp


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import com.example.store.R
import com.example.store.ui.theme.BackgroundMain
import com.example.store.ui.theme.Blue
import com.example.store.ui.theme.StoreTheme
import com.example.store.ui.theme.myShapes
import com.example.store.ui.theme.textHeaderStyleRegular
import com.example.store.ui.theme.textHelperStyleSmall


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
            MainCardView {

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
fun MainCardView(SignUpEvent: () -> Unit) {

    var name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

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
            ) { name.value = it }

            MainTextField(
                edtValue = email.value, icon = R.drawable.ic_email, hint = "Email Address"
            ) { email.value = it }

            PasswordTextField(
                edtValue = password.value, icon = R.drawable.ic_password, hint = "Password"
            ) { password.value = it }

            PasswordTextField(
                edtValue = confirmPassword.value,
                icon = R.drawable.ic_password,
                hint = "Confirm Password",
            ) { confirmPassword.value = it }

            Button(
                onClick = { SignUpEvent() },
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
                TextButton(onClick = { }) {
                    Text(text = "Login", color = Blue, style = textHelperStyleSmall)
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






