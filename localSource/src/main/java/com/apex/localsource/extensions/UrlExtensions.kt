package com.apex.localsource.extensions

import android.util.Log

fun getPageIntFromUrl(url: String?): Int? {
    return try {
        if (url == null) return null
        url.substringAfterLast("=").toInt()
    } catch (e: Exception) {
        e.message?.let { Log.d("converting url", it) }
        null
    }
}
