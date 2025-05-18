package com.tasklist.test

import com.tasklist.BaseTestClass
import com.tasklist.domain.repository.IPostFavoritesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import org.junit.Test
import org.koin.test.inject
import java.util.UUID
import kotlin.test.assertEquals

class DbTest : BaseTestClass() {

    @Test
    fun testInsert() {
        test {
            val rep = inject<IPostFavoritesRepository>().value
            val currentSize = rep.getAllFavorites.firstOrNull().orEmpty().size
            val randomBody = UUID.randomUUID().toString()

            val isExistRandomBody = rep.getAllFavorites.firstOrNull().orEmpty().any { it.body == randomBody }
            assertEquals(isExistRandomBody, false)

            rep.insertFavorites(
                rep.getAllFavorites.firstOrNull().orEmpty().random().copy(
                    body = randomBody
                )
            )
            delay(100)

            val currentSizeNew = rep.getAllFavorites.firstOrNull().orEmpty().size
            val isExistRandomBodyNew = rep.getAllFavorites.firstOrNull().orEmpty().any { it.body == randomBody }
            assertEquals(isExistRandomBodyNew, true)
            assertEquals(currentSize, currentSizeNew)
        }
    }

    @Test
    fun testDelete() {
        test {
            val rep = inject<IPostFavoritesRepository>().value
            val currentSize = rep.getAllFavorites.firstOrNull().orEmpty().size

            rep.deleteFavorites(
                rep.getAllFavorites.firstOrNull().orEmpty().random()
            )
            delay(100)

            assertEquals(rep.getAllFavorites.firstOrNull().orEmpty().size, currentSize - 1)
        }
    }

}