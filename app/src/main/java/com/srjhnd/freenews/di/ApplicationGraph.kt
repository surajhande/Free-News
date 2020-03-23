package com.srjhnd.freenews.di

import android.app.Application
import com.srjhnd.freenews.MainFragment
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

    fun inject(mainFragment: MainFragment)
}