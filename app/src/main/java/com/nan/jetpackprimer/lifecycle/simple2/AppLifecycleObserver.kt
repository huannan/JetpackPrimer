package com.nan.jetpackprimer.lifecycle.simple2

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

abstract class AppLifecycleObserver : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> onAppStateChanged(true)
            Lifecycle.Event.ON_STOP -> onAppStateChanged(false)
        }
    }

    abstract fun onAppStateChanged(isFront: Boolean)

}