package com.leonardo.pani.weatherapp

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getOrAwaitValue(): T {

    var data: T? = null

    val latch = CountDownLatch(1)


    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            this@getOrAwaitValue.removeObserver(this)
            latch.countDown()
        }

    }

    this.observeForever(observer)
    try {
        if (!latch.await(4, TimeUnit.SECONDS)) {
            throw TimeoutException("Exceeded time limit")
        }
    } finally {
        this.removeObserver(observer)
    }

    return data as T


}