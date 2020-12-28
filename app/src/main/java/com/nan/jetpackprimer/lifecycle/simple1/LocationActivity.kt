package com.nan.jetpackprimer.lifecycle.simple1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * LifeCycle基本使用
 */
class LocationActivity : AppCompatActivity() {

    private lateinit var locationListener: LocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationListener = LocationListener(this, lifecycle) {

        }
        lifecycle.addObserver(locationListener)

        locationListener.test()
    }

    override fun onResume() {
        super.onResume()
        locationListener.test()
    }

    /* 不需要再像以前一样写一堆代码
    override fun onStart() {
        super.onStart()
        locationListener.start()
    }

    override fun onStop() {
        super.onStop()
        locationListener.stop()
    }
    */
}