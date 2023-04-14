package com.example.urbandictionaryn.dependencyinjection

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DictionaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DictionaryApplication)
            modules(DictionaryModule)
        }
    }
}