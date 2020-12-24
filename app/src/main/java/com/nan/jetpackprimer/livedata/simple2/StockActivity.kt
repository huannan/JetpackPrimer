package com.nan.jetpackprimer.livedata.simple2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nan.jetpackprimer.R
import kotlinx.android.synthetic.main.activity_stock.*

/**
 * LiveData扩展
 */
class StockActivity : AppCompatActivity() {

    private val viewModel: StockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        viewModel.stock1LiveData.observe(this) { price ->
            tvStock1.text = "贵州茅台最新股价是$price"
        }

        viewModel.stock2LiveData.observe(this) { price ->
            tvStock2.text = "五粮液最新股价是$price"
        }
    }

}