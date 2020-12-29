package com.nan.jetpackprimer.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserInfoViewModel : ViewModel() {

    val users: MutableLiveData<List<User>> = MutableLiveData()

    fun query() {
        viewModelScope.launch {
            val allUsers = AppDatabase.userDao().getAll()
            users.value = allUsers
        }
    }

    fun insert(user: User) {
        viewModelScope.launch {
            AppDatabase.userDao().insert(user)
        }
    }

}