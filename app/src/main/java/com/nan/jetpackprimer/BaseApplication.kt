package com.nan.jetpackprimer

import android.app.Application
import android.content.Context
import android.util.Log
import com.nan.jetpackprimer.lifecycle.simple2.AppLifecycleObserver
import com.nan.jetpackprimer.lifecycle.simple2.AppLifecycleOwner

class BaseApplication : Application() {

    companion object {
        private const val TAG = "BaseApplication"
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this;
        AppLifecycleOwner.init(this)
        AppLifecycleOwner.lifecycle.addObserver(object : AppLifecycleObserver() {
            override fun onAppStateChanged(isFront: Boolean) {
                Log.e(TAG, "onAppStateChanged: $isFront")
            }
        })
    }

}