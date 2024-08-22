package ir.dunijet.dunibazaar.ui.features.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.dunijet.dunibazaar.model.data.Product
import ir.dunijet.dunibazaar.model.repository.product.ProductRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    val dataProducts = mutableStateOf<List<Product>>(listOf())

    fun loadByCategory(category: String) {

        viewModelScope.launch {

            val dataFromLocal = productRepository.getAllProductsByCategory(category)
            dataProducts.value = dataFromLocal
        }
    }
}