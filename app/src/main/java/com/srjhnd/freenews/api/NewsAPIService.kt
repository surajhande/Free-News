package com.srjhnd.freenews.api

import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject


interface NewsAPIService {

    // https://newsapi.org/v2/top-headlines?country=us&apiKey=<KEY>
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") key: String
    ) : HeadlineResponse
}