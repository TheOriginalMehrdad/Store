package ir.dunijet.dunibazaar.model.data


import com.google.gson.annotations.SerializedName

data class CommentResponse(
    val comments: List<Comment>,
    val success: Boolean
)


data class Comment(
    val commentId: String,
    val text: String,
    val userEmail: String
)

data class AddNewCommentResponse(
    val message: String,
    val success: Boolean
)