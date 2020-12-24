package com.nan.jetpackprimer.livedata.simple4

data class BaseResult<T>(val code: String, val msg: String, val data: T)
