package com.nan.jetpackprimer.livedata.simple3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.concurrent.thread

object PostalCodeRepository {

    /**
     * 模拟获取邮政编码
     */
    fun getPostCode(address: String): LiveData<String> {
        val postCodeLiveData: MutableLiveData<String> = MutableLiveData()
        thread {
            Thread.sleep(3000L)
            postCodeLiveData.postValue("519000")
        }
        return postCodeLiveData
    }

}