package com.nan.jetpackprimer.room

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nan.jetpackprimer.R
import kotlinx.android.synthetic.main.activity_user_info_room.*

/**
 * ROOM示例
 */
class UserInfoActivity : AppCompatActivity() {

    private val viewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_room)

        viewModel.users.observe(this) { users ->
            val sb = StringBuilder()
            users.forEach { user ->
                sb.append(user.toString()).append("\n")
            }
            tvUsers.text = sb.toString()
        }

        btnInsert.setOnClickListener {
            viewModel.insert(User(1, "Wu", "Huannan", null))
        }

        btnQuery.setOnClickListener {
            viewModel.query()
        }
    }

}