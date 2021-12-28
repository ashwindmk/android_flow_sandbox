package com.ashwin.android.flowsandbox.stateflow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ashwin.android.flowsandbox.databinding.ActivityStateFlowBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

class StateFlowActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStateFlowBinding
    private lateinit var viewModel: StateFlowViewModel

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStateFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(StateFlowViewModel::class.java)

        updateTimer()

        binding.stopFlowButton.setOnClickListener {
            job?.cancel()
        }

        binding.startFlowButton.setOnClickListener {
            job?.cancel()
            updateTimer()
        }

        binding.stateFlowButton.setOnClickListener {
            openStateFlowActivity()
        }

        binding.checkStateButton.setOnClickListener {
            checkLastState()
        }
    }

    private fun updateTimer() {
        Log.d("flow-sandbox", "state-flow: updateTimer")
        job = lifecycleScope.launchWhenStarted {
            Log.d("flow-sandbox", "state-flow: started (${Thread.currentThread().name})")

            // Live sync
            viewModel.timerStateFlow.collect {
                Log.d("flow-sandbox", "${System.identityHashCode(this@StateFlowActivity)}: state-flow: time: $it (${Thread.currentThread().name})")
                binding.timerTextview.text = it.toString()
            }
        }
    }

    private fun checkLastState() {
        val state =  viewModel.timerStateFlow.value
        Log.d("flow-sandbox", "${System.identityHashCode(this@StateFlowActivity)}: state-flow: last-state: $state (${Thread.currentThread().name})")
        binding.timerTextview.text = state.toString()
    }

    override fun onStart() {
        super.onStart()
        Log.d("flow-sandbox", "state-flow: onStart")
    }

    private fun openStateFlowActivity() {
        val intent = Intent(this, StateFlowActivity::class.java)
        startActivity(intent)
    }
}
