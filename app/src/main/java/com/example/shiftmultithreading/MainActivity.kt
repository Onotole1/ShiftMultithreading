package com.example.shiftmultithreading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class MainActivity : AppCompatActivity() {

    private val fibonacciFlow = flow {
        var a = 0
        var b = 1

        emit(1)

        while (true) {
            emit(a + b)

            val tmp = a + b
            a = b
            b = tmp
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            fibonacciFlow.map {
                it.toString()
            }
                    .flowOn(Dispatchers.Default)
                    .onEach { result ->
                        delay(1000)
                        resultView.text = result
                    }
                    .launchIn(lifecycleScope)
        }
    }
}
