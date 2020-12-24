package com.nan.jetpackprimer.viewmodel.simple1

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nan.jetpackprimer.R
import kotlinx.android.synthetic.main.activity_user_info.*

/**
 * ViewModel基本使用
 * 尝试旋转屏幕
 */
class UserInfoActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "UserInfoActivity"
    }

    private val viewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        viewModel.user.observe(this) { user ->
            tvUser.text = user.toString()
        }

        Log.d(TAG, "onCreate: $viewModel")
    }

}