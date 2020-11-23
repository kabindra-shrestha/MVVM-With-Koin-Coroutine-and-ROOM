package com.kabindra.sample

import androidx.multidex.MultiDexApplication
import com.kabindra.sample.di.*
import com.kabindra.sample.util.plantLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        plantLogger()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                apiModule,
                viewModelModule,
                repositoryModule,
                networkModule,
                databaseModule
            )
        }
    }
}