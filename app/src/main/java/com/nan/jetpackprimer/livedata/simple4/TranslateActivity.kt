package com.nan.jetpackprimer.livedata.simple4

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.nan.jetpackprimer.R
import kotlinx.android.synthetic.main.activity_translate.*

/**
 * LiveData结合协程
 */
class TranslateActivity : AppCompatActivity() {

    private val viewModel: TranslateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)

        viewModel.translateResult.observe(this) { result ->
            val translateResult = result.getOrNull()
            if (null == translateResult) {
                tvResult.text = "翻译失败"
                return@observe
            }

            tvResult.text = translateResult.data
        }

        btnUpdate.setOnClickListener {
            viewModel.inputLiveData.value = edtInput.text.toString().trim()
        }
    }

}