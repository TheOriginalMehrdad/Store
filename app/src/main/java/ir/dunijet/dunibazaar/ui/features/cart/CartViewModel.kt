package ir.dunijet.dunibazaar.ui.features.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.dunijet.dunibazaar.model.data.Product
import ir.dunijet.dunibazaar.model.repository.cart.CartRepository
import ir.dunijet.dunibazaar.model.repository.user.UserRepository
import ir.dunijet.dunibazaar.util.coroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val productList = mutableStateOf(listOf<Product>())
    val totalPrice = mutableStateOf(0)
    val isChangingNumber = mutableStateOf(Pair("", false))

    fun loadCartData() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val data = cartRepository.getUserCartInfo()
            productList.value = data.productList
            totalPrice.value = data.totalPrice
        }
    }

    fun addItem(productId: String) {

        viewModelScope.launch(coroutineExceptionHandler) {

            isChangingNumber.value = isChangingNumber.value.copy(productId, true)
            val isSuccess = cartRepository.addToCart(productId)

            if (isSuccess) {
                loadCartData()
            }

            delay(300)

            isChangingNumber.value = isChangingNumber.value.copy(productId, false)
        }
    }

    fun removeItem(productId: String) {

        viewModelScope.launch(coroutineExceptionHandler) {

            isChangingNumber.value = isChangingNumber.value.copy(productId, true)
            val isSuccess = cartRepository.removeFromCart(productId)

            if (isSuccess) {
                loadCartData()
            }

            delay(300)

            isChangingNumber.value = isChangingNumber.value.copy(productId, false)
        }
    }

    fun getUserLocation(): Pair<String, String> {
        return userRepository.getUserLocation()
    }

    fun setUserLocation(address: String, postalCode: String) {
        userRepository.saveUserLocation(address, postalCode)
    }

    fun purchaseAll(address: String, postalCode: String, isSuccess: (Boolean, String) -> Unit) {

        viewModelScope.launch(coroutineExceptionHandler) {

            val result = cartRepository.submitOrder(address, postalCode)

            isSuccess(result.success, result.paymentLink)
        }
    }

    fun setPaymentStatus(status: Int) {
        cartRepository.setPurchaseStatus(status)
    }
}







