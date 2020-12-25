package com.nan.jetpackprimer.workermanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    companion object {
        private const val TAG = "UploadWorker"
    }

    override fun doWork(): Result {
        val url = inputData.getString("IMAGE_URL")
        url?.let {
            uploadImages(url)
            // 从 doWork() 返回的 Result 会通知 WorkManager 服务工作是否成功，以及工作失败时是否应重试工作。
            return Result.success()
        } ?: return Result.success()
    }

    private fun uploadImages(url: String) {
        Thread.sleep(3000L)
        Log.e(TAG, "uploadImages success $url")
    }

}