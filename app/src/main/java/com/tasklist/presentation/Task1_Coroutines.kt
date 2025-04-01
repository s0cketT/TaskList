package com.tasklist.presentation

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

class Task1_Coroutines {

    fun main() {
        val mScope = MainScope() + Dispatchers.IO
        val job = mScope.launch {
            (0..100).forEach {

                    val result = test(it)
                    Log.e("!!!", "${result}")

            }
        }

        mScope.launch {
            delay(3_500)
            job.cancel()
            Log.e("!!!", "отмена")
        }
    }


    suspend fun test(id: Int): String {
        withContext(Dispatchers.IO) {
            Thread.sleep(1000)
        }
        return id.toString()
    }

}