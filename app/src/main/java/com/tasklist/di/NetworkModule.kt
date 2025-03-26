package com.tasklist.di

import com.tasklist.OkhttpCache.setOkhttpCache
import com.tasklist.data.remote.IPostApi
import com.tasklist.data.remote.ICommentsApi
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .setOkhttpCache(androidApplication())
    }

    single<IPostApi> { get<Retrofit>().create(IPostApi::class.java) }
    single<ICommentsApi> { get<Retrofit>().create(ICommentsApi::class.java) }
}