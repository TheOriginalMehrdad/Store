package ir.dunijet.dunibazaar.ui.features.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import ir.dunijet.dunibazaar.R
import ir.dunijet.dunibazaar.model.data.Product
import ir.dunijet.dunibazaar.ui.theme.Blue
import ir.dunijet.dunibazaar.ui.theme.Shapes
import ir.dunijet.dunibazaar.util.MyScreens

@Composable
fun CategoryScreen(categoryName: String) {

    val navigation = getNavController()
    val viewModel = getNavViewModel<CategoryViewModel>()
    viewModel.loadByCategory(categoryName)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        CategoryToolBar(categoryName)

        val data = viewModel.dataProducts

        CategoryList(data.value) {
            navigation.navigate(MyScreens.ProductScreen.route + "/" + it)
        }
    }
}

@Composable
fun CategoryItem(data: Product, onProductClicked: (String) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 18.dp, end = 18.dp, top = 18.dp)
            .clickable { onProductClicked.invoke(data.productId) },
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
                        text = "$${data.price}",
                        fontSize = 16.sp,
                    )
                }

                Surface(
                    shape = Shapes.medium,
                    color = Blue,
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Bottom)
                        .padding(bottom = 3.dp, end = 9.dp)
                ) {
                    Text(
                        text = "${data.soldItem} Sold",
                        fontSize = 13.sp,
                        color = Color.White,
                        modifier = Modifier.padding(
                            end = 9.dp,
                            start = 9.dp,
                            top = 6.dp,
                            bottom = 6.dp
                        )
                    )
                }

            }
        }
    }
}

@Composable
fun CategoryList(data: List<Product>, onProductClicked: (String) -> Unit) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 18.dp)
    ) {
        items(data.size) {
            CategoryItem(data[it], onProductClicked)
        }
    }
}

@Composable
fun CategoryToolBar(categoryName: String) {

    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White,
        title = {
            Text(
                text = categoryName,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    )
}























