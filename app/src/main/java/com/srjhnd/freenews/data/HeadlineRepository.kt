package com.srjhnd.freenews.data

import androidx.lifecycle.LiveData
import com.srjhnd.freenews.api.HeadlineResponse
import com.srjhnd.freenews.api.NewsAPIService
import com.srjhnd.freenews.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    suspend fun insertHeadline(headline: Headline) = headlineDAO.insert(headline)
    suspend fun updateHeadline(headline: Headline) = headlineDAO.update(headline)
    suspend fun deleteHeadline(headline: Headline) = headlineDAO.delete(headline)

    fun getCountAsync() = CoroutineScope(IO).async {
        headlineDAO.getCount()
    }

    fun fetchTopHeadlines(): Flow<HeadlineResponse> = flow {
        try {
            val response =
                newsAPIService.getTopHeadlines(NetworkUtils.country, NetworkUtils.API_KEY)
            response.articles.forEach { println("source fetched ${it.source?.name} and ${it.source?.id}") }
            emit(response)
        } catch (e: Exception) {
            emit(HeadlineResponse())
            Timber.d("error occurred : ${e.printStackTrace()}")
        }

//        val response = newsAPIService.getTopHeadlines(NetworkUtils.country, NetworkUtils.API_KEY)
//        Timber.d("response result: " + response.results)
//        val headlines = response.articles
//        headlines.apply { cacheResponse(headlines) }
    }

    private suspend fun cacheResponse(headlines: List<Headline>) {
        for (headline in headlines)
            headlineDAO.insert(headline)
    }
}