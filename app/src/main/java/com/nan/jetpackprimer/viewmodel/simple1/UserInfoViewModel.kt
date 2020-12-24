package com.nan.jetpackprimer.viewmodel.simple1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class UserInfoViewModel : ViewModel() {

    val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            loadUser()
        }
    }

    private fun loadUser() {
        thread {
            Thread.sleep(3000L)
            user.postValue(User("Wu", "Huannan"))
        }
    }
}