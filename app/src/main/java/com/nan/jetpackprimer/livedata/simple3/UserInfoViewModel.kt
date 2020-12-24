package com.nan.jetpackprimer.livedata.simple3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class UserInfoViewModel : ViewModel() {

    val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    /**
     * LiveData<User>转换为LiveData<String>
     */
    val userName: LiveData<String> = Transformations.map(user) { user ->
        "${user.name}${user.lastName}"
    }

    //---------------------------

    /**
     * 假设有一个接口是提供LiveData的形式返回数据的
     * 不用转换操作，你需要这样获取地址，那么UI组件每次获取都要先注销之前的LiveData
     */
    fun getPostCodeUgly(address: String): LiveData<String> {
        // DON'T DO THIS
        return PostalCodeRepository.getPostCode(address)
    }

    /**
     * 使用switchMap对输入进行转换，优化代码
     * 此机制允许较低级别的应用创建以延迟的方式按需计算的 LiveData 对象
     * 每次操作addressInput，switchMap内部就会去获取更新邮政编码
     */
    val addressInput = MutableLiveData<String>()

    val postalCode: LiveData<String> = Transformations.switchMap(addressInput) {
        PostalCodeRepository.getPostCode(it)
    }
}