package com.demo.coroutineflow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

val coroutineScope = CoroutineScope(Dispatchers.IO)

suspend fun main() {
    val flow = getFlow()

    val job1 = coroutineScope.launch {
        println(flow.first())
    }
    delay(3000)
    val job2 = coroutineScope.launch {
        flow.collect {
            println(it)
        }
    }
    job1.join()
    job2.join()
}

fun getFlow(): Flow<Int> = flow {
    repeat(3) {
        println("Emitted: $it")
        emit(it)
        delay(1000)
    }
}