package com.mahmoud_ashraf.menustask

import android.app.Application
import com.mahmoud_ashraf.data.di.networkModule
import com.mahmoud_ashraf.data.di.remoteDataSourceModule
import com.mahmoud_ashraf.data.di.repositoryModule
import com.mahmoud_ashraf.domain.menu.di.usecaseModule
import com.mahmoud_ashraf.menustask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            // Koin logger
            androidLogger()
            // inject Android context
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    remoteDataSourceModule,
                    repositoryModule,
                    usecaseModule,
                    viewModelModule
                )
            )
        }
    }


}