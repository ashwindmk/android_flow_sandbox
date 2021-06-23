package com.ashwin.android.flowsandbox.sharedflow

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashwin.android.flowsandbox.DummyStream
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SharedFlowViewModel : ViewModel() {
    private val _numberSharedFlow = MutableSharedFlow<Int>(10)
    val numberSharedFlow = _numberSharedFlow.asSharedFlow()

    init {
        startNumberFlow()
    }

    fun startNumberFlow() {
        viewModelScope.launch {
            DummyStream.fetchNumberStream(20)
                .collect {
                    Log.d("flow-sandbox", "shared-flow-viewmodel: ${System.identityHashCode(this)}: $it")
                    _numberSharedFlow.emit(it)
                }
        }
    }
}
