package com.nan.jetpackprimer.viewmodel.simple2

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val selected = MutableLiveData<User>()

    fun select(user: User) {
        selected.value = user
    }

}