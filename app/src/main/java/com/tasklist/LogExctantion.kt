package com.tasklist

import android.util.Log

fun log(msg: String) {
    if(BuildConfig.DEBUG) {
        runCatching {
            Log.d("!!!", msg)
        }.getOrElse {
            println(msg)
        }
    }
}