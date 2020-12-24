package com.nan.jetpackprimer.livedata.simple3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class UserInfoViewModel : ViewModel() {

    val user1: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    /**
     * LiveData<User>转换为LiveData<String>
     */
    val userName: LiveData<String> = Transformations.map(user1) { user ->
        "${user.name}${user.lastName}"
    }

    //---------------------------

    val userId: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private fun getUser(userId: String): LiveData<User> {
        val userLiveData: MutableLiveData<User> = MutableLiveData()
        thread {
            Thread.sleep(3000L)
            userLiveData.postValue(User("Wu", "Huannan"))
        }
        return userLiveData
    }

    /**
     * LiveData<String>转换为LiveData<User>
     * switchMap中必须返回LiveData
     */
    val user2: LiveData<User> = Transformations.switchMap(userId) { userId ->
        getUser(userId)
    }

}