package ir.dunijet.dunibazaar.ui.features.product

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import ir.dunijet.dunibazaar.R
import ir.dunijet.dunibazaar.model.data.Comment
import ir.dunijet.dunibazaar.model.data.Product
import ir.dunijet.dunibazaar.ui.theme.Blue
import ir.dunijet.dunibazaar.ui.theme.PriceBackground
import ir.dunijet.dunibazaar.ui.theme.Shapes
import ir.dunijet.dunibazaar.util.MyScreens
import ir.dunijet.dunibazaar.util.NetworkChecker
import ir.dunijet.dunibazaar.util.stylePrice


@Preview(showBackground = true)
@Composable
fun ProductScreenPreView() {
    ProductScreen(productId = " ")
}


@Composable
fun ProductScreen(productId: String) {

    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getNavViewModel<ProductViewModel>()
    viewModel.loadData(productId, NetworkChecker(context).isInternetConnected)


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 54.dp)
        ) {
            ProductToolBar(
                productName = "Details",
                badgeNumber = viewModel.badgeNumber.value,
                onBackClicked = { navigation.popBackStack() },
                onCartClicked = {

                    if (NetworkChecker(context).isInternetConnected) {
                        navigation.navigate(MyScreens.CartScreen.route)
                    } else {
                        Toast.makeText(
                            context,
                            "Please check the network connection first!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )

            val comments =
                if (NetworkChecker(context).isInternetConnected) viewModel.comments.value else listOf()
            ProductItem(
                data = viewModel.thisProduct.value,
                comments = comments,
                onCategoryClicked = {
                    navigation.navigate(MyScreens.CategoryScreen.route + "/" + it)
                },
                onAddNewCommentClicked = {
                    viewModel.addNewComment(productId, it) { message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        AddToCart(
            viewModel.thisProduct.value.price,
            viewModel.isAddingProduct.value
        ) {
            if (NetworkChecker(context).isInternetConnected) {

                viewModel.addProductToCart(productId) {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }

            } else {

                Toast.makeText(context, "Please connect to the internet first!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

}


@Composable
fun ProductToolBar(
    productName: String,
    badgeNumber: Int,
    onBackClicked: () -> Unit,
    onCartClicked: () -> Unit
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
                text = productName,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 11.dp)


            )
        },
        actions = {
            IconButton(onClick = { onCartClicked.invoke() }) {
                if (badgeNumber == 0) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                } else {
                    BadgedBox(badge = { Badge { Text(text = badgeNumber.toString()) } }) {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                    }
                }
            }
        }
    )
}


@Composable
fun AddToCart(
    price: String,
    isAddingProduct: Boolean,
    onCartClicked: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val fraction =
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 0.20f else 0.09f
    Surface(
        color = Color.White,
        modifier = Modifier
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
                    .padding(18.dp)
                    .width(136.dp)
                    .height(54.dp),
                onClick = { onCartClicked.invoke() }) {

                if (isAddingProduct) {
                    DotsTyping()
                } else {
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier,
                        text = "Add to cart",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .padding(18.dp)
                    .height(54.dp),
                shape = Shapes.large,
                color = PriceBackground
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stylePrice(price),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(
                        9.dp
                    )
                )
            }

        }
    }
}

@Composable
fun ProductDesign(data: Product, onCategoryClicked: (String) -> Unit) {

    AsyncImage(
        model = data.imgUrl,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(199.dp),
        contentScale = ContentScale.Crop
    )

    Text(
        text = data.name,
        modifier = Modifier.padding(top = 9.dp, start = 9.dp),
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    )

    Text(
        text = data.detailText,
        modifier = Modifier.padding(9.dp),
        style = TextStyle(
            fontSize = 13.sp,
            textAlign = TextAlign.Justify
        )
    )

    TextButton(onClick = { onCategoryClicked.invoke(data.category) }) {

        Text(
            text = "#${data.category}",
            style = TextStyle(fontSize = 11.sp)
        )
    }
}

@Composable
fun ProductItem(
    data: Product,
    comments: List<Comment>,
    onCategoryClicked: (String) -> Unit,
    onAddNewCommentClicked: (String) -> Unit
) {

    Column(
        modifier = Modifier.padding(bottom = 27.dp)
    ) {
        ProductDesign(data = data, onCategoryClicked)

        Divider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
        )

        ProductDetail(data = data, commentNumber = comments.size.toString())

        Divider(
            thickness = 1.dp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 18.dp, bottom = 18.dp)
        )

        ProductComments(comments = comments, onAddNewCommentClicked)
    }

}

@Composable
fun ProductDetail(data: Product, commentNumber: String) {

    val context = LocalContext.current
    val textComment =
        if (NetworkChecker(context).isInternetConnected) "$commentNumber comments" else "No Network Connection!"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 9.dp, top = 9.dp, bottom = 9.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_details_comment),
                    contentDescription = null,
                    modifier = Modifier.size(27.dp)
                )

                Text(
                    text = textComment,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 9.dp, top = 9.dp, bottom = 9.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_details_material),
                    contentDescription = null,
                    modifier = Modifier.size(27.dp)
                )

                Text(
                    text = data.material,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 9.dp, top = 9.dp, bottom = 18.dp)
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_details_sold),
                    contentDescription = null,
                    modifier = Modifier.size(27.dp)
                )

                Text(
                    text = data.soldItem + " sold",
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 13.sp
                )
            }

        }

        Surface(
            color = Blue,
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 18.dp, end = 9.dp),
            shape = Shapes.large
        ) {
            Text(
                text = data.tags,
                color = Color.White,
                modifier = Modifier.padding(6.dp),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun CommentBody(comment: Comment) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp),
        elevation = 0.dp,
        border = BorderStroke(1.dp, Color.LightGray),
        shape = Shapes.large
    ) {

        Column(
            modifier = Modifier.padding(13.dp)
        ) {

            Text(
                text = comment.userEmail,
                modifier = Modifier.padding(9.dp),
                style = TextStyle(fontSize = 16.sp),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = comment.text,
                modifier = Modifier.padding(9.dp),
                style = TextStyle(fontSize = 13.sp),
            )
        }
    }
}

@Composable
fun ProductComments(comments: List<Comment>, AddNewComment: (String) -> Unit) {

    val showCommentDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (comments.isNotEmpty()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier.padding(start = 9.dp),
                text = "Comments",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            TextButton(
                onClick = {
                    if (NetworkChecker(context).isInternetConnected) {
                        showCommentDialog.value = true
                    } else {
                        Toast.makeText(
                            context,
                            "Please connect to the internet first!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Text(
                    text = "Add new Comment",
                )
            }
        }
        comments.forEach {
            CommentBody(comment = it)
        }
    } else {

        TextButton(onClick = {
            if (NetworkChecker(context).isInternetConnected) {
                showCommentDialog.value = true
            } else {
                Toast.makeText(
                    context,
                    "Please connect to the internet first!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }) {

            Text(
                text = "Add new comment",
                fontSize = 13.sp,
            )
        }
    }

    if (showCommentDialog.value) {

        AddNewCommentDialog(OnDismiss = { showCommentDialog.value = false }) {
            AddNewComment.invoke(it)
        }
    }
}

@Composable
fun AddNewCommentDialog(
    OnDismiss: () -> Unit,
    OnPositiveClicked: (String) -> Unit
) {
    val context = LocalContext.current
    val userComment = remember { mutableStateOf("") }

    Dialog(onDismissRequest = OnDismiss) {

        Card(
            modifier = Modifier.fillMaxHeight(0.49f),
            elevation = 9.dp,
            shape = Shapes.medium
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(9.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Write your comment",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(9.dp)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(9.dp))

                MainTextFieldComment(
                    edtValue = userComment.value,
                    hint = "Write something"
                ) {
                    userComment.value = it
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { OnDismiss.invoke() }) {
                        Text(text = "Cancel")
                    }

                    Spacer(modifier = Modifier.padding(9.dp))

                    TextButton(onClick = {

                        if (userComment.value.isNotEmpty() && userComment.value.isNotBlank()) {

                            if (NetworkChecker(context).isInternetConnected) {
                                OnPositiveClicked.invoke(userComment.value)
                                OnDismiss.invoke()
                            }

                        } else {
                            Toast.makeText(
                                context,
                                "Please write something to comment first!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                        Text(text = "Post Comment")
                    }
                }
            }
        }
    }
}

@Composable
fun MainTextFieldComment(
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

@Composable
fun DotsTyping() {

    val dotSize = 10.dp
    val delayUnit = 350
    val maxOffset = 10f

    @Composable
    fun Dot(
        offset: Float
    ) = Spacer(
        Modifier
            .size(dotSize)
            .offset(y = -offset.dp)
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .padding(start = 8.dp, end = 8.dp)
    )

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                0f at delay with LinearEasing
                maxOffset at delay + delayUnit with LinearEasing
                0f at delay + delayUnit * 2
            }
        )
    )

    val offset1 by animateOffsetWithDelay(0)
    val offset2 by animateOffsetWithDelay(delayUnit)
    val offset3 by animateOffsetWithDelay(delayUnit * 2)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = maxOffset.dp)
    ) {
        val spaceSize = 2.dp

        Dot(offset1)
        Spacer(Modifier.width(spaceSize))
        Dot(offset2)
        Spacer(Modifier.width(spaceSize))
        Dot(offset3)
    }
}









