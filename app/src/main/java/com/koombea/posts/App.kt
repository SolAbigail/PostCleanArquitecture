package com.koombea.posts

import android.app.Application
import androidx.multidex.MultiDex
import com.couchbase.lite.CouchbaseLite
import com.koombea.posts.di.AppModule
import com.koombea.posts.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        CouchbaseLite.init(this@App)

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(AppModule, NetworkModule))
        }
    }
}