package com.tasklist.presentation

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import java.util.UUID

class Task4_Coroutines {

    data class News(
        val id: String,
        val text: String
    )

    suspend fun getNewsByID(id: String): News = withContext(Dispatchers.IO) {
        delay(1000)
        return@withContext News(id = id, text = UUID.randomUUID().toString())
    }

    suspend fun getAllNewsIDs(): List<String> = withContext(Dispatchers.IO) {
        delay(2000)
        return@withContext (0 until 1000).map { it.toString() }
    }


    //Async
     suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
         getAllNewsIDs().map { async { getNewsByID(it) } }.awaitAll()
     }

    //Semaphore
    /*suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
        val ids = getAllNewsIDs()
        val newsList = mutableListOf<News>()
        val semaphore = Semaphore(100)

        ids.map { id ->
            async {
                semaphore.withPermit {
                    val news = getNewsByID(id)
                    synchronized(newsList) {
                        newsList.add(news)
                    }
                }
            }
        }.awaitAll()

        return@withContext newsList
    }*/

    //Mutex
    /*suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
        val ids = getAllNewsIDs()
        val newsList = mutableListOf<News>()
        val mutex = Mutex()

        ids.map { id ->
            launch {
                val news = getNewsByID(id)
                mutex.withLock {
                    newsList.add(news)
                }
            }
        }.joinAll()

        return@withContext newsList
    }*/

    //forEachIndexed
    /*suspend fun fetchAllNews(): List<News> = withContext(Dispatchers.IO) {
        val ids = getAllNewsIDs()
        val newsList = mutableListOf<News>()

        ids.forEachIndexed { index, id ->
            launch {

                newsList.add(getNewsByID(id))
            }
        }

        return@withContext newsList
    }*/


    fun main() {
        MainScope().launch(Dispatchers.IO) {
            Log.e("!!!", "start")
            val result = fetchAllNews()
            Log.d("!!!", result.toString())
            Log.e("!!!", "${result.size}")
        }
    }

}

