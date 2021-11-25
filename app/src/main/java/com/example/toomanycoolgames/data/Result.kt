package com.example.toomanycoolgames.data

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

fun <T> computeResult(result: Result<T>, afterError: () -> Unit = {}): T? {
    return if (result is Result.Success) {
        result.data
    } else {
        afterError.invoke()
        null
    }
}

fun <T> computeResult(result: Result<List<T>>, afterError: () -> Unit = {}): List<T>? {
    return if (result is Result.Success) {
        result.data
    } else {
        afterError.invoke()
        null
    }
}