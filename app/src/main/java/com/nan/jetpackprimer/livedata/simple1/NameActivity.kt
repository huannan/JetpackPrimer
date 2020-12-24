package com.nan.jetpackprimer.livedata.simple1

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nan.jetpackprimer.R
import kotlinx.android.synthetic.main.activity_name.*

/**
 * LiveData基本使用
 */
class NameActivity : AppCompatActivity() {

    private val viewModel: NameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        viewModel.currentName.observe(this, Observer<String> { newName ->
            tvName.text = newName
        })

        btnUpdate.setOnClickListener {
            viewModel.currentName.value = "Huannan"
        }
    }

}