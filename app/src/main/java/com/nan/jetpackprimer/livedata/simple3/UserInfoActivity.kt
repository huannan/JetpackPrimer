package com.nan.jetpackprimer.livedata.simple3

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nan.jetpackprimer.R
import kotlinx.android.synthetic.main.activity_user_info.*

/**
 * LiveData转换
 */
class UserInfoActivity : AppCompatActivity() {

    private val viewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        viewModel.userName.observe(this, Observer<String> { userName ->
            tvUser.text = userName
        })

        btnUpdate1.setOnClickListener {
            viewModel.user.value = User("Wu", "Huannan")
        }

        //---------------------------

        viewModel.postalCode.observe(this) { postalCode ->
            tvPostalCode.text = postalCode
        }

        btnUpdate2.setOnClickListener {
            viewModel.addressInput.value = "珠海"
        }
    }

}