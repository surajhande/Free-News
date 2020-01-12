package com.srjhnd.opennews.data

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class HeadlineRepository private constructor(private val headlineDAO: HeadlineDAO) {
    val allHeadlines: LiveData<List<Headline>> = headlineDAO.getHeadlines()

    companion object {
        private var instance: HeadlineRepository? = null

        fun getInstance(headlineDAO: HeadlineDAO): HeadlineRepository {
            return instance?: synchronized(this) {
                instance ?: HeadlineRepository(headlineDAO).also { instance = it }
            }
        }
    }


    class InsertHeadlineAsyncTask(val headlineDAO: HeadlineDAO) :
        AsyncTask<Headline, Any?, Any?>() {
        override fun doInBackground(vararg params: Headline?): Any? {
            if (params[0] != null)
                headlineDAO.insert(params[0]!!)
            return null
        }
    }

    class UpdateHeadlineAsyncTask(val headlineDAO: HeadlineDAO) :
        AsyncTask<Headline, Any?, Any?>() {
        override fun doInBackground(vararg params: Headline?): Any? {
            if (params[0] != null)
                headlineDAO.update(params[0]!!)
            return null
        }
    }

    class DeleteHeadlineAsyncTask(val headlineDAO: HeadlineDAO) :
        AsyncTask<Headline, Any?, Any?>() {
        override fun doInBackground(vararg params: Headline?): Any? {
            if (params[0] != null)
                headlineDAO.delete(params[0]!!)
            return null
        }
    }

    fun inserHeadline(headline: Headline) = InsertHeadlineAsyncTask(headlineDAO).execute()
    fun updateHeadline(headline: Headline) = UpdateHeadlineAsyncTask(headlineDAO).execute()
    fun deleteHeadline(headline: Headline) = DeleteHeadlineAsyncTask(headlineDAO).execute()
}