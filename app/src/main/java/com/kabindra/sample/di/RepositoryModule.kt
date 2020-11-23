package com.kabindra.sample.di

import android.content.Context
import com.kabindra.sample.SampleApi
import com.kabindra.sample.db.UserDao
import com.kabindra.sample.repository.user.UserRepository
import com.kabindra.sample.repository.user.UserRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    fun provideUserRepository(api: SampleApi, context: Context, dao: UserDao): UserRepository {
        return UserRepositoryImpl(api, context, dao)
    }
    single { provideUserRepository(get(), androidContext(), get()) }

}