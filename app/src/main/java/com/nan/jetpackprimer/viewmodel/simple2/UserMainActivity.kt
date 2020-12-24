package com.nan.jetpackprimer.viewmodel.simple2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nan.jetpackprimer.R

/**
 * ViewModel共享
 *
 * 请注意，这两个 Fragment 都会检索包含它们的 Activity。这样，当这两个 Fragment 各自获取 ViewModelProvider 时，它们会收到相同的 SharedViewModel 实例（其范围限定为该 Activity）。

 * 此方法具有以下优势：
 * Activity 不需要执行任何操作，也不需要对此通信有任何了解。
 * 除了 SharedViewModel 约定之外，Fragment 不需要相互了解。如果其中一个 Fragment 消失，另一个 Fragment 将继续照常工作。
 * 每个 Fragment 都有自己的生命周期，而不受另一个 Fragment 的生命周期的影响。如果一个 Fragment 替换另一个 Fragment，界面将继续工作而没有任何问题。
 */
class UserMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer1, UserMasterFragment())
            .add(R.id.fragmentContainer2, UserDetailFragment())
            .commit()
    }
}