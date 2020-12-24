package com.nan.jetpackprimer.livedata.simple2

import androidx.lifecycle.ViewModel

class StockViewModel : ViewModel() {

    val stock1LiveData = StockLiveData.get("贵州茅台")
    val stock2LiveData = StockLiveData.get("五粮液")

}