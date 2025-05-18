package com.tasklist

import android.app.Application
import com.tasklist.di.appModule
import com.tasklist.mock.ApiService_Mock
import com.tasklist.mock.FavoritesDao_Mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito

open class BaseTestClass : KoinTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testModuleList =  appModule + listOf(
        ApiService_Mock().mModule(),
        FavoritesDao_Mock().mModule()
    )


    @Before
    fun before() {
        startKoin {
            androidContext(Mockito.mock(Application::class.java))
            modules(testModuleList)
        }
        Dispatchers.setMain(testDispatcher)
    }


    @After
    fun after() {
        stopKoin()
        Dispatchers.resetMain()

    }


    fun test(
        invoke: suspend () -> Unit
    ) {
        runBlocking {
            invoke()
        }
    }

    fun <T> Flow<T>.parseEvent() = this.stateIn(MainScope(), SharingStarted.Eagerly, null)

}