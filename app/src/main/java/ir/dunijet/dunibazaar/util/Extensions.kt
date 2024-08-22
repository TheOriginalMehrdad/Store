package ir.dunijet.dunibazaar.util

import android.annotation.SuppressLint
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import java.text.SimpleDateFormat
import java.util.*

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.v("error", throwable.message.toString())
}

fun stylePrice(oldPrice: String): String {

    if (oldPrice.length > 3) {

        val reserved = oldPrice.reversed()
        var newPrice = ""

        for (i in oldPrice.indices) {

            newPrice += reserved[i]
            if ((i + 1) % 3 == 0) {
                newPrice += ','
            }
        }
        val readyToGo = newPrice.reversed()
        if (readyToGo.first() == ',') {
            return "$" + readyToGo.substring(1)
        }
        return "$" + readyToGo
    }
    return "$" + oldPrice
}

@SuppressLint("SimpleDateFormat")
fun styleTime(timeInMilli: Long): String {

    val formatter = SimpleDateFormat("yyyy/MM/dd hh:mm")

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMilli

    return formatter.format(calendar.time)
}