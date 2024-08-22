package ir.dunijet.dunibazaar.model.repository.comment

import com.google.gson.JsonObject
import ir.dunijet.dunibazaar.model.data.Comment
import ir.dunijet.dunibazaar.model.net.ApiService

class CommentRepositoryImpl(
    private val apiService: ApiService
) : CommentRepository {


    override suspend fun getAllComments(productId: String): List<Comment> {

        val jsonObject = JsonObject().apply {
            addProperty("productId", productId)
        }
        val data = apiService.getAllComments(jsonObject)

        if (data.success) {
            return data.comments
        }
        return listOf()

    }

    override suspend fun addNewComment(
        productId: String,
        text: String,
        isSuccess: (String) -> Unit
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("productId", productId)
            addProperty("text", text)
        }
        val resul = apiService.addNewComment(jsonObject)

        if (resul.success) {
            isSuccess.invoke(resul.message)
        } else {
            isSuccess.invoke("Comment was not added successfully")
        }

    }
}