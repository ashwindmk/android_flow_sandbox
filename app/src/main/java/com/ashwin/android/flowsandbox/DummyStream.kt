package com.ashwin.android.flowsandbox

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

object DummyStream {
    fun fetchTimeStream() = flow {
        while (true) {
            delay(2000L)
            emit(System.currentTimeMillis())
        }
    }

    fun fetchNumberStream(n: Int = 20) = flow<Int> {
        for (i in 1 .. n) {
            delay(1000L)
            emit(i)
        }
    }
}
