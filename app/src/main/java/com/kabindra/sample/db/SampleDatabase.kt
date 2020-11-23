package com.kabindra.sample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kabindra.sample.db.converter.*
import com.kabindra.sample.db.model.*

@Database(
    entities = [UserData::class, Languages::class, Name::class, Native::class, Translations::class],
    version = 1, exportSchema = false
)

@TypeConverters(
    Converters::class,
    LanguagesTypeConverter::class,
    NameConverter::class,
    NativeConverter::class,
    TranslationsConverter::class
)
abstract class SampleDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}