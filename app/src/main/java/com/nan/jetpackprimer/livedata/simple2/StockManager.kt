package com.nan.jetpackprimer.livedata.simple2

import java.math.BigDecimal
import kotlin.concurrent.thread

object StockManager {

    private val listeners = mutableMapOf<String, (BigDecimal) -> Unit>()

    fun requestPriceUpdates(symbol: String, listener: (BigDecimal) -> Unit) {
        listeners[symbol] = listener
        thread {
            Thread.sleep(3000L)
            when (symbol) {
                "贵州茅台" -> listeners[symbol]?.invoke(BigDecimal(1900))
                "五粮液" -> listeners[symbol]?.invoke(BigDecimal(280))
            }
        }
    }

    fun removeUpdates(symbol: String) {
        listeners.remove(symbol)
    }

}