package com.srjhnd.freenews.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.srjhnd.freenews.api.NewsAPIService
import com.srjhnd.freenews.data.ApplicationDatabase
import com.srjhnd.freenews.data.HeadlineDAO
import com.srjhnd.freenews.data.HeadlineRepository
import com.srjhnd.freenews.utils.NetworkUtils
import com.srjhnd.freenews.viewmodel.HeadlineViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {

    @Provides
    fun provideNewsAPIService(): NewsAPIService {
        return Retrofit.Builder()
            .baseUrl(NetworkUtils.BASE_URL)
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
    fun provideHeadlineViewModelFactory(repository: HeadlineRepository): HeadlineViewModelFactory {
        return HeadlineViewModelFactory(repository)
    }
}