package com.nan.jetpackprimer.livedata.simple2

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import java.math.BigDecimal

class StockLiveData private constructor(private val symbol: String) : LiveData<BigDecimal>() {

    companion object {
        private const val TAG = "StockLiveData"
        private lateinit var sInstance: StockLiveData

        @MainThread
        fun get(symbol: String): StockLiveData {
            sInstance = if (::sInstance.isInitialized) sInstance else StockLiveData(symbol)
            return sInstance
        }

    }

    private val listener = { price: BigDecimal ->
        postValue(price)
    }

    override fun onActive() {
        super.onActive()
        Log.d(TAG, "onActive")
        StockManager.requestPriceUpdates(symbol, listener)
    }

    override fun onInactive() {
        super.onInactive()
        Log.d(TAG, "onInactive")
        StockManager.removeUpdates(symbol)
    }
}