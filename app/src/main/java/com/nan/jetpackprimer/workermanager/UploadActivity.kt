package com.nan.jetpackprimer.workermanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.nan.jetpackprimer.R
import kotlinx.android.synthetic.main.activity_upload.*
import java.util.concurrent.TimeUnit

/**
 * WorkManager基本使用
 *
 * 关于工作链、观察等，后面用到的时候再补充
 */
class UploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        btnUpload.setOnClickListener {
            testOneTimeWorkRequest()
            // testPeriodicWorkRequest()
        }
    }

    /**
     * 调度一次性工作
     */
    private fun testOneTimeWorkRequest() {
        // val uploadWorkRequest: WorkRequest = OneTimeWorkRequest.from(UploadWorker)

        // 添加工作约束
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        val uploadWorkRequest: OneTimeWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setConstraints(constraints)
            // 延迟工作
            .setInitialDelay(10, TimeUnit.SECONDS)
            // 重试和退避政策
            // 短退避延迟时间设置为允许的最小值，即 10 秒。由于政策为 LINEAR，每次尝试重试时，重试间隔都会增加约 10 秒。
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            // 标记工作
            // 每个工作请求都有一个唯一标识符，该标识符可用于在以后标识该工作，以便取消工作或观察其进度。
            // 如果有一组在逻辑上相关的工作，对这些工作项进行标记可能也会很有帮助。通过标记，您一起处理一组工作请求。
            .addTag("upload")
            // 设置输入数据
            .setInputData(
                workDataOf(
                    "IMAGE_URL" to "http://..."
                )
            )
            .build()

        WorkManager
            .getInstance(this)
            .enqueue(uploadWorkRequest)

        //  会取消带有特定标记的所有工作请求
        // WorkManager.getInstance(this).cancelAllWorkByTag("upload")
        // 返回一个 WorkInfo 对象列表，该列表可用于确定当前工作状态
        // val workInfosByTag = WorkManager.getInstance(this).getWorkInfosByTag("upload")

        // 工作链接
        /*
        WorkManager.getInstance(this)
            .beginWith(uploadWorkRequest)
            .then(uploadWorkRequest)
            .enqueue()
        */
    }

    /**
     * 调度定期工作
     */
    private fun testPeriodicWorkRequest() {
        /*
        val uploadWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<UploadWorker>(1, TimeUnit.HOURS, 15, TimeUnit.MINUTES)
                .build()
        */
        val uploadWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<UploadWorker>(15, TimeUnit.MINUTES)
                .build()

        WorkManager
            .getInstance(this)
            .enqueue(uploadWorkRequest)
    }
}