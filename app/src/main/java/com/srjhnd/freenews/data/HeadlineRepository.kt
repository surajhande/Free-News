package com.srjhnd.freenews.data

import androidx.lifecycle.LiveData
import com.srjhnd.freenews.api.HeadlineResponse
import com.srjhnd.freenews.api.NewsAPIService
import com.srjhnd.freenews.api.NoConnectivityException
import com.srjhnd.freenews.utils.NetworkUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeadlineRepository @Inject constructor(
    private val headlineDAO: HeadlineDAO,
    private val newsAPIService: NewsAPIService
) {
    val allHeadlines: LiveData<List<Headline>> = headlineDAO.getHeadlines()

    suspend fun insertHeadline(headline: Headline) = headlineDAO.insert(headline)
    suspend fun updateHeadline(headline: Headline) = headlineDAO.update(headline)
    suspend fun deleteHeadline(headline: Headline) = headlineDAO.delete(headline)

    fun getHeadlineByIdAsync(title: String) = CoroutineScope(IO).async {
        headlineDAO.getHeadline(title)
    }

    fun getCountAsync() = CoroutineScope(IO).async {
        headlineDAO.getCount()
    }

    fun fetchTopHeadlines(): Flow<HeadlineResponse> = flow {
        try {
            val response =
                newsAPIService.getTopHeadlines(NetworkUtils.country, NetworkUtils.API_KEY)
            response.articles.forEach { println("source fetched ${it.source?.name} and ${it.source?.id}") }
            emit(response)
        } catch (ne: NoConnectivityException) {
            emit(HeadlineResponse("ERROR"))
            Timber.d("error occured: ${ne.message + ne.printStackTrace()}")
        } catch (e: Exception) {
            emit(HeadlineResponse("ERROR"))
            Timber.d("error occurred : ${e.printStackTrace()}")
        }
    }

    private suspend fun cacheResponse(headlines: List<Headline>) {
        for (headline in headlines)
            headlineDAO.insert(headline)
    }
}