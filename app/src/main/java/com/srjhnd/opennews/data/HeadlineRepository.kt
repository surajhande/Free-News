package com.srjhnd.opennews.data

import androidx.lifecycle.LiveData
import com.srjhnd.opennews.api.HeadlineResponse
import com.srjhnd.opennews.api.NewsAPIService
import com.srjhnd.opennews.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class HeadlineRepository private constructor(
    private val headlineDAO: HeadlineDAO,
    private val newsAPIService: NewsAPIService
) {
    val allHeadlines: LiveData<List<Headline>> = headlineDAO.getHeadlines()

    companion object {
        private var instance: HeadlineRepository? = null

        fun getInstance(
            headlineDAO: HeadlineDAO,
            newsAPIService: NewsAPIService
        ): HeadlineRepository {
            return instance ?: synchronized(this) {
                instance ?: HeadlineRepository(headlineDAO, newsAPIService).also { instance = it }
            }
        }
    }

    fun insertHeadline(headline: Headline) = CoroutineScope(IO).launch {
        headlineDAO.insert(headline)
    }

    fun updateHeadline(headline: Headline) = CoroutineScope(IO).launch {
        headlineDAO.update(headline)
    }

    fun deleteHeadline(headline: Headline) = CoroutineScope(IO).launch {
        headlineDAO.delete(headline)
    }

    fun getCountAsync() = CoroutineScope(IO).async {
        headlineDAO.getCount()
    }

    fun fetchTopHeadlines() {
        CoroutineScope(IO).launch {
            val response = newsAPIService.getTopHeadlines(NetworkUtils.country, NetworkUtils.API_KEY)
            val headlines = response.articles
            headlines.apply { cacheResponse(headlines) }
        }
    }

    private fun cacheResponse(headlines: List<Headline>) {
        CoroutineScope(IO).launch {
            for (headline in headlines)
                headlineDAO.insert(headline)
        }
    }
}