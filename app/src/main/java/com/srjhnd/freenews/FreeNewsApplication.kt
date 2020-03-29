package com.srjhnd.freenews

import android.app.Application
import androidx.work.*
import com.srjhnd.freenews.di.ApplicationGraph
import com.srjhnd.freenews.di.DaggerApplicationGraph
import com.srjhnd.freenews.utils.ClearOldHeadlinesWorker

class FreeNewsApplication : Application() {

    lateinit var applicationGraph: ApplicationGraph
    override fun onCreate() {
        super.onCreate()
        applicationGraph = DaggerApplicationGraph.factory().create(this)
        val dbWorkRequest = OneTimeWorkRequest.Builder(ClearOldHeadlinesWorker::class.java)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(dbWorkRequest)
    }
}