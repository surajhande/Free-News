package com.srjhnd.freenews.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.srjhnd.freenews.data.ApplicationDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

class ClearOldHeadlinesWorker(
    private val appContext: Context,
    private val params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result = coroutineScope {
        val appDatabase = ApplicationDatabase.getInstance(appContext)
        val headlineDao = appDatabase.headlineDAO()
        headlineDao.removeOldHeadlines()
        Result.success()
    }
}