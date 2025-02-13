package ir.dunijet.dunibazaar.ui.features.product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.dunijet.dunibazaar.model.data.Comment
import ir.dunijet.dunibazaar.model.repository.cart.CartRepository
import ir.dunijet.dunibazaar.model.repository.comment.CommentRepository
import ir.dunijet.dunibazaar.model.repository.product.ProductRepository
import ir.dunijet.dunibazaar.util.EMPTY_PRODUCT
import ir.dunijet.dunibazaar.util.coroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepository: ProductRepository,
    private val commentRepository: CommentRepository,
    private val cartRepository: CartRepository
) : ViewModel() {

    val thisProduct = mutableStateOf(EMPTY_PRODUCT)
    val comments = mutableStateOf(listOf<Comment>())
    val isAddingProduct = mutableStateOf(false)
    val badgeNumber = mutableStateOf(0)

    fun loadData(productId: String, isInternetConnected: Boolean) {

        loadProductFromCache(productId)

        if (isInternetConnected) {
            loadAllComponents(productId)
            loadBadgeNumber()
        }
    }

    private fun loadProductFromCache(productId: String) {

        viewModelScope.launch(coroutineExceptionHandler) {
            thisProduct.value = productRepository.getProductById(productId)
        }
    }

    private fun loadAllComponents(productId: String) {

        viewModelScope.launch(coroutineExceptionHandler) {
            comments.value = commentRepository.getAllComments(productId)
        }
    }

    fun addNewComment(productId: String, text: String, isSuccess: (String) -> Unit) {
        viewModelScope.launch(coroutineExceptionHandler) {
            commentRepository.addNewComment(productId, text, isSuccess)
            delay(100)
            comments.value = commentRepository.getAllComments(productId)
        }
    }

    fun addProductToCart(productId: String, AddingToCartResult: (String) -> Unit) {

        viewModelScope.launch(coroutineExceptionHandler) {

            isAddingProduct.value = true

            val result = cartRepository.addToCart(productId)
            delay(666)

            isAddingProduct.value = false

            if (result) {
                AddingToCartResult.invoke("The product was added to cart")
            } else {
                AddingToCartResult.invoke("The product was not added to cart")
            }
        }
    }

    private fun loadBadgeNumber() {

        viewModelScope.launch(coroutineExceptionHandler) {

            badgeNumber.value = cartRepository.getCartSize()
        }
    }
}