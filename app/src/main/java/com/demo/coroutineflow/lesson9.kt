package com.demo.coroutineflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

private val coroutineScope = CoroutineScope(Dispatchers.IO)

suspend fun main() {
    val flow = MutableSharedFlow<Int>()

    coroutineScope.launch {
        repeat(5) {
            println("Emitted: $it")
            flow.emit(it)
            delay(1000)
        }
    }

    val job1 = coroutineScope.launch {
        flow.collect {
            println("Got from 1st collector: $it")
        }
    }
    delay(3000)
    val job2 = coroutineScope.launch {
        flow.collect {
            println("Got from 2nd collector: $it")
        }
    }
    job1.join()
    job2.join()
}