package com.ashwin.android.flowsandbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ashwin.android.flowsandbox.databinding.ActivityMainBinding
import com.ashwin.android.flowsandbox.sharedflow.SharedFlowActivity
import com.ashwin.android.flowsandbox.stateflow.StateFlowActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.testFlowButton.setOnClickListener {
            testFlow()
        }

        binding.stateFlowButton.setOnClickListener {
            openStateFlowActivity()
        }

        binding.sharedFlowButton.setOnClickListener {
            openSharedFlowActivity()
        }
    }

    private fun testFlow() {
        val flow = flow<Int> {
            // Runs within a coroutine
            for (i in 1..100) {
                emit(i)
                delay(1000L)
            }
        }

        GlobalScope.launch {
            flow
//                .buffer()  // Stores values from producer to solve back-pressure problem
//                .filter {
//                    it % 2 == 0
//                }.map {
//                    it * it
//                }
                .collect {
                    Log.d("flow-sandbox", "MainActivity: flow collect: $it (${Thread.currentThread().name})")
//                    delay(2000L)
                }
        }
    }

    private fun openStateFlowActivity() {
        val intent = Intent(this, StateFlowActivity::class.java)
        startActivity(intent)
    }

    private fun openSharedFlowActivity() {
        val intent = Intent(this, SharedFlowActivity::class.java)
        startActivity(intent)
    }
}
