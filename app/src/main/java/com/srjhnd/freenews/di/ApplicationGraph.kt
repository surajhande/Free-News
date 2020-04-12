package com.srjhnd.freenews.di

import android.app.Application
import com.srjhnd.freenews.FeedFragment
import com.srjhnd.freenews.HeadlineFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationGraph {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): ApplicationGraph
    }

    fun inject(feedFragment: FeedFragment)
    fun inject(headlineFragment: HeadlineFragment)

}