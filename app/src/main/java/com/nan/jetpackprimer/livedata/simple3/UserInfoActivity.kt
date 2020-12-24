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
            tvUser1.text = userName
        })

        btnUpdate1.setOnClickListener {
            viewModel.user1.value = User("Wu", "Huannan")
        }

        //---------------------------

        viewModel.user2.observe(this) { user ->
            tvUser2.text = user.toString()
        }

        btnUpdate2.setOnClickListener {
            viewModel.userId.value = "1"
        }
    }

}