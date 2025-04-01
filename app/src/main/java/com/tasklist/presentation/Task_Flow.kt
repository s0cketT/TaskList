package com.tasklist.presentation

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Timer
import java.util.TimerTask
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

class Task_Flow {


    fun getSimpleFlow(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(500)
            emit(i)
        }
    }

    fun getCallbackFlow(): Flow<String> = callbackFlow {
        val messages = listOf("Hello", "How are you?", "Goodbye")
        messages.forEach {
            trySend(it)
            delay(1000)

        }

        close()
    }

    fun getChannelFlow(): Flow<String> = channelFlow {

        launch {
            send("Task Started")
            delay(1000)
            send("Task In Progress")
            delay(1000)
            send("Task Completed")
        }
    }


    private val _sharedFlow = MutableSharedFlow<String>(replay = 2)
    val sharedFlow: SharedFlow<String> get() = _sharedFlow

    suspend fun emitToSharedFlow(value: String) {
        _sharedFlow.emit(value)
    }


    private val _stateFlow = MutableStateFlow("Initial State")
    val stateFlow: StateFlow<String> get() = _stateFlow

    fun updateStateFlow(value: String) {
        _stateFlow.update { value }
    }

    val scope = CoroutineScope(Dispatchers.IO)
    /*
    val flowToStateFlow = flow {
        Log.e("!!!", "Flow запущен")
        emit("Loading")
        delay(1000)
        emit("Loaded")
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = "Initial"
    )
    */

    /*
    val flowToSharedFlow = flow {
        Log.e("!!!", "Flow запущен")
        emit("Loading")
        delay(1000)
        emit("Loaded")
    }.shareIn(
        scope = scope,
        started = SharingStarted.Lazily,
    )
    */


    fun main(): Unit = runBlocking {
        val taskFlow = Task_Flow()

        /*//flow
        launch {
            taskFlow.getSimpleFlow().collect { Log.e("!!!", "SimpleFlow: $it") }
        }*/

        //callbackFlow
        /*
        launch {
            taskFlow.getCallbackFlow().collect {Log.e("!!!", "CallbackFlow: $it") }
        }
        */
        //ChannelFlow
        /*
        delay(3000)
        launch {
            taskFlow.getChannelFlow().collect { Log.e("!!!", "ChannelFlow: $it") }
        }
        */
        //SharedFlow
        /*
        launch {
            taskFlow.sharedFlow.collect { Log.e("!!!", "SharedFlow: $it") }
        }
        delay(2000)
        taskFlow.emitToSharedFlow("New Event in SharedFlow")
        */

        //StateFlow
        /*
        launch {
            taskFlow.stateFlow.collect { Log.e("!!!", "StateFlow: $it") }
        }
        delay(500)
        taskFlow.updateStateFlow("Updated State")
        */
        //FlowToStateFlow
        //delay(3000)
        //flowToStateFlow.collect { Log.e("!!!", "Получено: $it") }

        //FlowToStateFlowWhileSubscribed
        /*
        delay(2000)
        launch(scope.coroutineContext) {
            Log.e("!!!", "Подписчик 1 подключился")
            taskFlow.flowToStateFlow.collect { Log.e("!!!", "Подписчик 1: $it") }
        }
        */

        /*delay(6000)
        launch(scope.coroutineContext) {
            Log.e("!!!", "Подписчик 2 подключился")
            taskFlow.flowToStateFlow.collect { Log.e("!!!", "Подписчик 2: $it") }
        }*/



        //FlowToSharedFlow
        /*
        delay(3000)
        flowToSharedFlow.collect { Log.e("!!!", "Получено: $it") }
        */
        //FlowMap
        /*val flowMap = flowOf(1, 2, 3)
            .map { it * 2 }
            .collect{  Log.e("!!!", "$it") }

        */

        /*val flowFilter = flowOf(1, 2, 3, 4, 5)
            .filter { it % 2 == 0 }
            .collect{  Log.e("!!!", "$it") }
        */
        /*
        val flowDebounce = flow {
            emit(1)
            delay(100)
            emit(2)
            delay(400)
            emit(3)
        }.debounce(300).collect{  Log.e("!!!", "$it") }
        */

        /*
        val flowTake = flowOf(1, 2, 3, 4, 5)
            .take(3)
            .collect{  Log.e("!!!", "$it") }
        */
        /*
        val flowBuffer = flow {
            repeat(3) {
                delay(1000)
                emit(it)
            }
        }.buffer().collect{  Log.e("!!!", "$it") }
        */
        /*
        val flowOn = flow {
            emit("работа")
        }.flowOn(Dispatchers.IO)
            .collect{  Log.e("!!!", "$it") }
        */
        /*
        val flowDrop = flowOf(1, 2, 3, 4, 5)
            .drop(2)
            .collect{  Log.e("!!!", "$it") }
        */
        /*
        val flowDistinct = flowOf(1, 1, 2, 2, 3, 3, 3, 4)
            .distinctUntilChanged()
            .collect{  Log.e("!!!", "$it") }
        */

        //FlowCombineZip
        /*
        val flow1 = flowOf(1, 2, 3).onEach { delay(300) }
        val flow2 = flowOf("A", "B", "C", "D").onEach { delay(600) }
        flow1.combine(flow2) { num, letter ->
            "$num$letter"
        }.collect{  Log.e("!!!", "$it") }
        */



        //first / firstOrNull
        /*
        val value = flowOf(1, 2, 3).first()
        val emptyFlow = emptyFlow<Int>()
        */
        //Scan
        /*
        val flowScan = flowOf(1, 2, 3, 4)
            .scan(0) { acc, value -> acc + value }
            .collect { Log.e("!!!", "$it") }
        */

        /*
        val flow = flowOf(1, 2, 3)
        val result = flow.flatMapLatest { value ->
            flow {
                emit("$value-A")
                delay(500)
                emit("$value-B")
            }
        }
        result.collect { Log.e("!!!", "$it") }
        */

        /*
        Log.e("!!!", "Программа запущена")
        val result = waitForTime()
        Log.e("!!!", "Результат: $result")
        Log.e("!!!", "Программа завершена")
        */
    }
}

suspend fun waitForTime(): String = suspendCancellableCoroutine { continuation ->
    Log.e("!!!", "Ожидание 3 секунды")

    Timer().schedule(object : TimerTask() {
        override fun run() {
            continuation.resume("Таймер завершён!")
        }
    }, 3000)


    continuation.invokeOnCancellation {
        Log.e("!!!", "Таймер был отменён")
    }
}


