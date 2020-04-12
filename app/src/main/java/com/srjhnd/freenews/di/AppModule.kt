package com.srjhnd.freenews.di

import android.app.Application
import com.srjhnd.freenews.api.NetworkConnectivityInterceptor
import com.srjhnd.freenews.api.NewsAPIService
import com.srjhnd.freenews.data.ApplicationDatabase
import com.srjhnd.freenews.data.HeadlineDAO
import com.srjhnd.freenews.data.HeadlineRepository
import com.srjhnd.freenews.utils.NetworkUtils
import com.srjhnd.freenews.viewmodel.FeedViewModelFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {

    @Provides
    fun provideNewsAPIService(app: Application): NewsAPIService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectivityInterceptor(app.applicationContext))
            .build()
        return Retrofit.Builder()
            .baseUrl(NetworkUtils.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    @Provides
    fun provideApplicationDatabase(app: Application): ApplicationDatabase {
        return ApplicationDatabase.getInstance(app.applicationContext)
    }

    @Provides
    fun provideHeadlineDao(appDb: ApplicationDatabase): HeadlineDAO {
        return appDb.headlineDAO()
    }

    @Provides
    fun provideHeadlineViewModelFactory(repository: HeadlineRepository): FeedViewModelFactory {
        return FeedViewModelFactory(repository)
    }
}