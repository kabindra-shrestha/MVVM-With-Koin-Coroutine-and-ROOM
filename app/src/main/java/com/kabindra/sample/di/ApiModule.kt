package com.kabindra.sample.di

import com.kabindra.sample.SampleApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideUserApi(retrofit: Retrofit): SampleApi {
        return retrofit.create(SampleApi::class.java)
    }
    single { provideUserApi(get()) }

}