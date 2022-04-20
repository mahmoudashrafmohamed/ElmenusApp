package com.mahmoud_ashraf.menustask

import android.app.Application
import com.mahmoud_ashraf.data.di.*
import com.mahmoud_ashraf.domain.menu.di.usecaseModule
import com.mahmoud_ashraf.menustask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

public lateinit var appContext: Application
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
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
                    roomModule,
                    localDataSourceModule,
                    repositoryModule,
                    usecaseModule,
                    viewModelModule
                )
            )
        }
    }


}