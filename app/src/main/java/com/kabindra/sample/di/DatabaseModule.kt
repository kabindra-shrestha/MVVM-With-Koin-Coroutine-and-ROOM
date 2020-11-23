package com.kabindra.sample.di

import android.app.Application
import androidx.room.Room
import com.kabindra.sample.db.UserDao
import com.kabindra.sample.db.SampleDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): SampleDatabase {
        return Room.databaseBuilder(application, SampleDatabase::class.java, "via_tv_ott")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideUserDao(database: SampleDatabase): UserDao {
        return database.userDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }


}
