package com.ashwin.android.flowsandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flow = flow<Int> {
            // Runs within a coroutine
            for (i in 1..10) {
                emit(i)
                delay(1000L)
            }
        }

        GlobalScope.launch {
            flow
                 .buffer()  // Stores values from producer to solve back-pressure problem
                 .filter {
                     it % 2 == 0
                 }.map {
                     it * it
                 }.collect {
                    println(it)
                    delay(2000L)
                }
        }
    }
}