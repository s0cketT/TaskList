package com.tasklist.presentation

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext

class Task2_Coroutines {

    fun main() {
        val mScope = MainScope() + Dispatchers.IO
        val job = mScope.launch() {
            (0..100).forEach {
                ensureActive()

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


    fun test(id: Int): String {
        Thread.sleep(1000)
        return id.toString()
    }

}