package com.hackathon

import com.hackathon.di.ILogger
import com.hackathon.di.appModule
import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

/**
 * CicekSepeti app entry point
 */
class MyApplication : Application() {
    private val logger: ILogger by inject()

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin Dependency Injection with modules from [appModule]
        startKoin(this, listOf(appModule))
    }
}