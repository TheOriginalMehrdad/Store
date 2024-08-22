package ir.dunijet.dunibazaar.ui.features.cart

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import ir.dunijet.dunibazaar.R
import ir.dunijet.dunibazaar.model.data.Product
import ir.dunijet.dunibazaar.ui.features.profile.AddUserLocationDataDialog
import ir.dunijet.dunibazaar.ui.theme.Blue
import ir.dunijet.dunibazaar.ui.theme.PriceBackground
import ir.dunijet.dunibazaar.ui.theme.Shapes
import ir.dunijet.dunibazaar.util.*

@Composable
fun CartScreen() {

    val context = LocalContext.current
    val getDataDialogState = remember { mutableStateOf(false) }
    val navigation = getNavController()
    val viewModel = getNavViewModel<CartViewModel>()
    viewModel.loadCartData()


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 74.dp)
        ) {

            CartToolBar(
                onBackClicked = { navigation.popBackStack() },
                onProfileClicked = { navigation.navigate(MyScreens.ProfileScreen.route) })

            if (viewModel.productList.value.isNotEmpty()) {

                CartList(
                    data = viewModel.productList.value,
                    isChangingNumber = viewModel.isChangingNumber.value,
                    onAddItemClicked = { viewModel.addItem(it) },
                    onRemoveItemClicked = { viewModel.removeItem(it) },
                    onItemClicked = { navigation.navigate(MyScreens.ProductScreen.route + "/" + it) })

            } else {
                NoDataMainAnimation()
            }
        }

        PurchaseAll(totalPrice = viewModel.totalPrice.value.toString()) {

            if (viewModel.productList.value.isNotEmpty()) {

                val locationData = viewModel.getUserLocation()

                if (locationData.first == "click to add" || locationData.second == "click to add") {
                    getDataDialogState.value = true
                } else {
                    viewModel.purchaseAll(
                        locationData.first,
                        locationData.second
                    ) { success, link ->

                        if (success) {

                            Toast.makeText(context, "Pay using zarinpal portal", Toast.LENGTH_SHORT)
                                .show()

                            viewModel.setPaymentStatus(PAYMENT_PENDING)

                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                            context.startActivity(intent)

                        } else {
                            Toast.makeText(
                                context,
                                "There are some issues in payment process!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            } else {
                Toast.makeText(context, "Please add some stuff first!", Toast.LENGTH_SHORT).show()
            }
        }

        if (getDataDialogState.value) {

            AddUserLocationDataDialog(
                showSaveLocation = true,
                onDismiss = { getDataDialogState.value = false },
                onSubmitClicked = { adress, postalCode, isChecked ->

                    if (NetworkChecker(context).isInternetConnected) {

                        if (isChecked) {
                            viewModel.setUserLocation(adress, postalCode)
                        }

                        viewModel.purchaseAll(
                            adress, postalCode
                        ) { success, link ->

                            if (success) {

                                Toast.makeText(
                                    context,
                                    "Pay using zarinpal portal",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()

                                viewModel.setPaymentStatus(PAYMENT_PENDING)

                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                                context.startActivity(intent)

                            } else {
                                Toast.makeText(
                                    context,
                                    "There are some issues in payment process!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    } else {
                        Toast.makeText(
                            context,
                            "Please connect to the internet first!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}

@Composable
fun CartItem(
    data: Product,
    isChangingNumber: Pair<String, Boolean>,
    onAddItemClicked: (String) -> Unit,
    onRemoveItemClicked: (String) -> Unit,
    onItemClicked: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 18.dp, end = 18.dp, top = 18.dp)
            .clickable { onItemClicked.invoke(data.productId) },
        elevation = 6.dp,
        shape = Shapes.large
    ) {
        Column {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(199.dp),
                model = data.imgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 3.dp, bottom = 9.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {

                    Text(
                        modifier = Modifier.padding(start = 6.dp, top = 6.dp),
                        text = data.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        modifier = Modifier.padding(start = 6.dp, top = 6.dp),
                        text = "From " + data.category + " Group",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        modifier = Modifier.padding(start = 6.dp, top = 18.dp),
                        text = "Product Authenticity Guarantee",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        modifier = Modifier.padding(start = 6.dp, top = 6.dp),
                        text = "Available Stock In Ship",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )


                    Surface(
                        modifier = Modifier
                            .padding(top = 18.dp, bottom = 6.dp)
                            .clip(Shapes.large),
                        color = PriceBackground
                    ) {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = stylePrice(
                                ((data.price.toInt()) * ((data.quantity ?: "1").toInt())).toString()
                            ),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }


                Surface(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Bottom)
                        .padding(bottom = 13.dp, end = 9.dp)
                ) {
                    Card(
                        border = BorderStroke(3.dp, Blue)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (data.quantity?.toInt() == 1) {

                                IconButton(
                                    onClick = { onRemoveItemClicked.invoke(data.productId) }) {

                                    Icon(
                                        modifier = Modifier.padding(end = 6.dp, start = 6.dp),
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = { onRemoveItemClicked.invoke(data.productId) }) {

                                    Icon(
                                        modifier = Modifier.padding(end = 6.dp, start = 6.dp),
                                        painter = painterResource(id = R.drawable.ic_minus),
                                        contentDescription = null
                                    )
                                }
                            }

                            if (isChangingNumber.first == data.productId && isChangingNumber.second) {

                                Text(
                                    text = "...",
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(bottom = 13.dp)
                                )
                            } else {

                                Text(
                                    text = data.quantity ?: "1",
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(bottom = 3.dp)
                                )
                            }

                            IconButton(
                                onClick = { onAddItemClicked.invoke(data.productId) }) {

                                Icon(
                                    modifier = Modifier.padding(end = 6.dp, start = 6.dp),
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun CartList(
    data: List<Product>,
    isChangingNumber: Pair<String, Boolean>,
    onAddItemClicked: (String) -> Unit,
    onRemoveItemClicked: (String) -> Unit,
    onItemClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 18.dp)
    ) {
        items(data.size) {
            CartItem(
                data = data[it],
                isChangingNumber = isChangingNumber,
                onAddItemClicked = onAddItemClicked,
                onRemoveItemClicked = onRemoveItemClicked,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
fun CartToolBar(
    onBackClicked: () -> Unit,
    onProfileClicked: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onBackClicked.invoke() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        elevation = 3.dp,
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Text(
                text = "My Cart",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 11.dp)
            )
        },
        actions = {
            IconButton(onClick = { onProfileClicked.invoke() }) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            }
        }
    )
}


@Composable
fun NoDataMainAnimation() {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_data))
    LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
}


@Composable
fun PurchaseAll(
    totalPrice: String,
    OnPurchaseClicked: () -> Unit
) {

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val fraction =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.15f else 0.07f

    Surface(
        color = Color.White, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(182.dp, 40.dp),
                onClick = {

                    if (NetworkChecker(context).isInternetConnected) {
                        OnPurchaseClicked.invoke()
                    } else {
                        Toast.makeText(context, "please connect to internet...", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            ) {

                Text(
                    modifier = Modifier.padding(2.dp),
                    text = "Let's Purchase !",
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium)
                )

            }

            Surface(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(Shapes.large),
                color = PriceBackground
            ) {

                Text(
                    modifier = Modifier.padding(
                        top = 6.dp,
                        bottom = 6.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),
                    text = "total : " + stylePrice(totalPrice),
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                )
            }

        }

    }

}
















