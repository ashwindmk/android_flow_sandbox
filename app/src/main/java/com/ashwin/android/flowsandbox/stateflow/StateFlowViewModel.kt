package com.ashwin.android.flowsandbox.stateflow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashwin.android.flowsandbox.DummyStream
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StateFlowViewModel: ViewModel() {
    private val _timerStateFlow = MutableStateFlow<Long>(0L)
    val timerStateFlow = _timerStateFlow.asStateFlow()

    init {
        startStateFlowTimer()
    }

    fun startStateFlowTimer() {
        viewModelScope.launch {
            DummyStream.fetchTimeStream().collect {
                Log.d("flow-sandbox", "state-flow-viewmodel: ${System.identityHashCode(this)}: $it")
                _timerStateFlow.value = it
            }
        }
    }
}
