package com.srjhnd.freenews

import android.app.Application
import com.srjhnd.freenews.di.ApplicationGraph
import com.srjhnd.freenews.di.DaggerApplicationGraph

class FreeNewsApplication : Application() {

    lateinit var applicationGraph: ApplicationGraph
    override fun onCreate() {
        super.onCreate()
        applicationGraph = DaggerApplicationGraph.factory().create(this)
    }
}