package ir.dunijet.dunibazaar.ui.features.profile

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import ir.dunijet.dunibazaar.R
import ir.dunijet.dunibazaar.ui.theme.Blue
import ir.dunijet.dunibazaar.ui.theme.Shapes
import ir.dunijet.dunibazaar.util.MyScreens
import ir.dunijet.dunibazaar.util.styleTime

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}

@Composable
fun ProfileScreen() {

    val context = LocalContext.current
    val viewModel = getNavViewModel<ProfileViewModel>()
    viewModel.loadUserData()

    val navigation = getNavController()

    Box {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileToolBar {
                navigation.popBackStack()
            }

            MainAnimation()

            Spacer(modifier = Modifier.padding(27.dp))

            ShowDataSection(
                subject = "Email Address",
                text = viewModel.email.value,
                null
            )

            ShowDataSection(
                subject = "Login Time",
                text = styleTime(viewModel.loginTime.value.toLong()),
                null
            )

            ShowDataSection(
                subject = "Address",
                text = viewModel.address.value
            ) { viewModel.showLocationDialog.value = true }


            ShowDataSection(
                subject = "Postal Code",
                text = viewModel.postalCode.value
            ) { viewModel.showLocationDialog.value = true }

            Button(
                onClick = {

                    Toast.makeText(context, "See you later", Toast.LENGTH_SHORT).show()

                    viewModel.signOut()

                    navigation.navigate(MyScreens.MainScreen.route) {
                        popUpTo(MyScreens.MainScreen.route) {
                            inclusive = true
                        }
                    }

                    navigation.popBackStack()
                    navigation.popBackStack()

                },
                modifier = Modifier.padding(18.dp)
            ) {
                Text(text = "Sign Out")
            }
        }


        if (viewModel.showLocationDialog.value) {

            AddUserLocationDataDialog(
                showSaveLocation = false,
                onDismiss = { viewModel.showLocationDialog.value = false },
                onSubmitClicked = { address, postalCode, _ ->
                    viewModel.setUserLocation(address, postalCode)
                })
        }
    }
}

@Composable
fun ProfileToolBar(onBackClicked: () -> Unit) {

    TopAppBar(
        title = {
            Text(
                text = "My Profile",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 58.dp)
            )
        },
        navigationIcon = {

            IconButton(onClick = { onBackClicked.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        elevation = 3.dp,
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxSize()

    )
}

@Composable
fun MainAnimation() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.profile_anim)
    )

    LottieAnimation(
        composition = composition,
        modifier = Modifier
            .size(266.dp)
            .padding(top = 18.dp, bottom = 18.dp),
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun ShowDataSection(
    subject: String,
    text: String,
    onLocationClicked: (() -> Unit)?
) {

    Column(
        modifier = Modifier
            .clickable { onLocationClicked?.invoke() },
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            modifier = Modifier.padding(start = 13.dp),
            text = subject,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Blue
        )

        Text(
            modifier = Modifier.padding(start = 13.dp, top = 9.dp),
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Divider(
            color = Blue,
            thickness = 0.6.dp,
            modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
        )
    }
}


@Composable
fun AddUserLocationDataDialog(
    showSaveLocation: Boolean,
    onDismiss: () -> Unit,
    onSubmitClicked: (String, String, Boolean) -> Unit
) {

    val context = LocalContext.current
    val checkedState = remember { mutableStateOf(true) }
    val userAddress = remember { mutableStateOf("") }
    val userPostalCode = remember { mutableStateOf("") }
    val fraction = if (showSaveLocation) 0.693f else 0.633f

    Dialog(onDismissRequest = { onDismiss.invoke() }) {

        Card(
            modifier = Modifier.fillMaxHeight(fraction),
            elevation = 9.dp,
            shape = Shapes.medium
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(9.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = "Add Location Data",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(9.dp)
                )

                Spacer(modifier = Modifier.height(9.dp))

                MainTextField(edtValue = userAddress.value, hint = "Write your address") {
                    userAddress.value = it
                }

                Spacer(modifier = Modifier.height(9.dp))

                MainTextField(edtValue = userPostalCode.value, hint = "Write your postal code") {
                    userPostalCode.value = it
                }

                if (!showSaveLocation) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 13.dp, start = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it })

                        Text(text = "Save to your profile")
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(onClick = { onDismiss.invoke() }) {
                        Text(text = "Cancel")
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    TextButton(onClick = {
                        if (userAddress.value.isNotEmpty() || userAddress.value.isNotBlank() &&
                            userPostalCode.value.isNotEmpty() || userPostalCode.value.isNotBlank()
                        ) {
                            onSubmitClicked(
                                userAddress.value,
                                userPostalCode.value,
                                checkedState.value
                            )
                            onDismiss.invoke()
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    "Please write something first!",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }) {
                        Text(text = "Done")
                    }
                }
            }
        }
    }
}


@Composable
fun MainTextField(
    edtValue: String, hint: String, onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        label = { Text(text = hint) },
        value = edtValue,
        singleLine = false,
        maxLines = 2,
        onValueChange = onValueChanged,
        placeholder = { Text(text = "Please write something ...") },
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = Shapes.medium
    )
}


















