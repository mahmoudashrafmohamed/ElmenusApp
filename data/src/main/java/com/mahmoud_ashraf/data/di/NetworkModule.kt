package com.mahmoud_ashraf.data.di

import android.app.Application
import android.content.Context
import com.mahmoud_ashraf.data.BuildConfig
import com.mahmoud_ashraf.data.core.interceptors.NetworkConnectionInterceptor
import com.mahmoud_ashraf.data.sources.remote.TagsApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { provideInterceptor() }
    single { provideNetworkConnectionInterceptor(androidApplication()) }
    single {
        provideOkHttpClient(
            get(),
            get()
        )
    }
    single {
        provideApi(
            get()
        )
    }
    single {
        provideRetrofit(
            get()
        )
    }
}

fun provideInterceptor(): Interceptor {
    return HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
}
fun provideNetworkConnectionInterceptor(appContext: Application): NetworkConnectionInterceptor {
    return NetworkConnectionInterceptor(appContext)
}


fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideOkHttpClient(interceptor: Interceptor,networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor(networkConnectionInterceptor)
        .build()
}

fun provideApi(retrofit: Retrofit): TagsApi = retrofit.create(TagsApi::class.java)