package com.srjhnd.freenews.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(
    appContext,
    params
) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open("init.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val headlineType = object : TypeToken<List<Headline>>() {}.type
                    val headlines: List<Headline> = Gson().fromJson(jsonReader, headlineType)

                    val database = ApplicationDatabase.getInstance(applicationContext)
                    database.headlineDAO().insertAll(headlines)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private val TAG = SeedDatabaseWorker::class.java.simpleName
    }
}