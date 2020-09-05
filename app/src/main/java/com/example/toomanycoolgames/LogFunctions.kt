package com.example.toomanycoolgames

import android.util.Log

fun Any.logError(exception: Throwable, message: () -> String) {
    Log.e(this.javaClass.simpleName, message(), exception)
}

fun Any.logDebug(message: () -> String) {
    Log.d(this.javaClass.simpleName, message())
}
