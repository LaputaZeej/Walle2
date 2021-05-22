package com.bugull.walle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.bugull.walle.ext.logv

/**
 * Author by xpl, Date on 2021/5/7.
 */
internal class WalleAppLifecycleObserver : LifecycleObserver {
    private val listeners: MutableList<OnAppChangedListener> = mutableListOf()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground() {
        if (DEBUG) ("$SUB_TAG::enterBackground ...${listeners.size}")
        listeners.forEach {
            it.onChanged(Status.Background)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground() {
        if (DEBUG) logv("$SUB_TAG::onForeground ...${listeners.size}")
        listeners.forEach {
            it.onChanged(Status.Foreground)
        }
    }

    fun addOnAppChangedListener(listener: OnAppChangedListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    fun removeOnAppChangedListener(listener: OnAppChangedListener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener)
        }
    }

    fun clear() {
        if (listeners.isNotEmpty()) {
            listeners.clear()
        }
    }

    companion object {
        private const val SUB_TAG = "App"
        private const val DEBUG = true
    }
}

sealed class Status {
    object Background : Status()
    object Foreground : Status()
}