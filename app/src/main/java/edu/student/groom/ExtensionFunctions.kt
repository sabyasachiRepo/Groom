package edu.student.groom

import com.google.gson.Gson
import edu.student.groom.data.APIErrorResponse
import okhttp3.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

fun String.letters() = filter { it.isLetter() }
val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun <T> retrofit2.Response<T>.getErrorMessage(): String? = errorBody()?.let {

    val errorResponse = Gson().fromJson(
        it.charStream(), APIErrorResponse::class.java
    )
    errorResponse.message
}

fun Throwable.isNetworkError():Boolean{
    return this is ConnectException || this is SocketTimeoutException
}

