package com.kabindra.sample.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kabindra.sample.db.model.Native


class NativeConverter {
    @TypeConverter
    fun stringToNative(json: String): Native? {
        val gson = Gson()
        val type = object : TypeToken<Native>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun nativeToString(name: Native): String {
        val gson = Gson()
        val type = object : TypeToken<Native>() {}.type
        return gson.toJson(name, type)
    }
}
