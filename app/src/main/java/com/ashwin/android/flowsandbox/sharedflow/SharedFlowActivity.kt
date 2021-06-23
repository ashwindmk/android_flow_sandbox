package com.ashwin.android.flowsandbox.sharedflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ashwin.android.flowsandbox.databinding.ActivitySharedFlowBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

class SharedFlowActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySharedFlowBinding
    private lateinit var viewModel: SharedFlowViewModel
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SharedFlowViewModel::class.java)

        binding.startFlowButton.setOnClickListener {
            job?.cancel()
            startSharedFlow()
        }

        binding.stopFlowButton.setOnClickListener {
            job?.cancel()
        }

        binding.replayCacheButton.setOnClickListener {
            replayCache()
        }
    }

    private fun startSharedFlow() {
        job = lifecycleScope.launchWhenStarted {
            Log.d("flow-sandbox", "shared-flow: ${System.identityHashCode(this)}: started")
            viewModel.numberSharedFlow.collect {
                // Receives all replay cached values sequentially without any delay
                Log.d("flow-sandbox", "shared-flow: ${System.identityHashCode(this)}: $it")
            }
        }
    }

    private fun replayCache() {
        val list = viewModel.numberSharedFlow.replayCache
        for (i in list) {
            Log.d("flow-sandbox", "shared-flow: replay-cache: $i")
        }
    }
}