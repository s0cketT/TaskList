package com.tasklist.presentation

import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
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
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import java.util.Timer
import java.util.TimerTask
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.time.Duration


class Task_Flow {


    fun getSimpleFlow(): Flow<Int> = flow {
        for (i in 1..5) {
            delay(1000)
            emit(i)
        }
    }

    fun fetchDataFromNetwork(): String {
        Thread.sleep(1000)
        return "Data from Network"
    }
    fun fetchDataFromDatabase(): String {
        Thread.sleep(500)
        return "Data from Database"
    }
    fun fetchData(): Flow<String> = channelFlow {
        launch {
            val networkData = fetchDataFromNetwork()
            send(networkData)
        }
        launch {
            val databaseData = fetchDataFromDatabase()
            send(databaseData)
        }
    }


    private val _sharedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)
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

    val flowToStateFlow = flow {
        Log.e("!!!", "Flow запущен")
        emit("Loading")
        delay(1_000)
        emit("Loaded")
        delay(10_000)
        emit("Loaded2")
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 5000,
            replayExpirationMillis = 400
        ),
        initialValue = "Initial"
    )


    val flowToSharedFlow = flow {
        Log.e("!!!", "Flow запущен")
        emit("Loading")
        delay(1000)
        emit("Loaded")
    }.shareIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(
            stopTimeoutMillis = 1000,
            replayExpirationMillis = 2000
        ),
        replay = 2,
    )



    fun startBluetoothConnectionUpdates(callback: (String) -> Unit) {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val status = if (Math.random() > 0.5) "Connected" else "Disconnected"
                callback(status)
            }
        }, 0, 2000)
    }
    fun bluetoothConnectionFlow(): Flow<String> = callbackFlow {
        startBluetoothConnectionUpdates { status ->
            trySend(status)
        }
        awaitClose {
            Log.e("!!!", "Bluetooth updates cancelled")
        }
    }

    fun main(): Unit = runBlocking {

       /* bluetoothConnectionFlow()
            .collect { status ->
                Log.e("!!!", "Bluetooth status: $status")
            }
        */

        val taskFlow = Task_Flow()


        //FlowToStateFlowWhileSubscribed
        /*
        val job = launch() {
            Log.e("!!!", "Подписчик 1 подключился")
            taskFlow.flowToStateFlow.collect { Log.e("!!!", "Подписчик 1: $it") }
        }

        delay(4000)
        job.cancel()
        delay(1500)
        launch() {
            Log.e("!!!", "Подписчик 2 подключился")
            taskFlow.flowToStateFlow.collect { Log.e("!!!", "Подписчик 2: $it") }
        }
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
        flow {
            for (i in 1..3) {
                emit(i)
                delay(5000) // Источник эмитирует каждые 100 мс
            }
        }.buffer()
            .collect {
                Log.e("!!!", "Collected: $it")
                delay(5000) // Обработка занимает 300 мс
            }
        */


        //FlowCombineZip

        val flow1 = flowOf(1, 2, 3).onEach { delay(300) }
        val flow2 = flowOf("A", "B", "C", "D").onEach { delay(600) }
        flow1.combine(flow2) { num, letter ->
            "$num$letter"
        }.collect{  Log.e("!!!", "$it") }



        //first / firstOrNull работает с suspend
        /*
        val value = flowOf(1, 2, 3).first()
        val emptyFlow = emptyFlow<Int>()
        */

        //Scan
        /*val flowScan = flowOf(1, 2, 3, 4)
            .scan(0) { acc, value -> acc + value }
            .collect { Log.e("!!!", "$it") }*/


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
        val result = performOperation()

        Log.e("!!!", "Результат: $result")
        if (result) {}
        else {}
        Log.e("!!!", "Программа завершена")
        */

    }
}


fun requestPermission(callback: (granted: Boolean) -> Unit) {
    Log.e("!!!", "Показываем диалог с запросом разрешения...")
    Thread.sleep(1500)

    val granted = false
    Log.e("!!!", "Пользователь выбрал: Запретить")
    callback(granted)
}


suspend fun performOperation(): Boolean = suspendCancellableCoroutine { continuation ->

    val callback = { granted: Boolean ->
        Log.e("!!!", "Возобновляем корутину с результатом: $granted")
        continuation.resume(granted)

    }

    requestPermission(callback)

    continuation.invokeOnCancellation {
        Log.e("!!!", "Корутинa была отменена. Закрываем диалог.")
    }
}



fun performOperation(callback: (Boolean) -> Unit) {
    val permissionCallback = { granted: Boolean ->
        Log.e("!!!", "Возобновляем выполнение с результатом: $granted")
        callback(granted)
    }

    requestPermission(permissionCallback)
}


