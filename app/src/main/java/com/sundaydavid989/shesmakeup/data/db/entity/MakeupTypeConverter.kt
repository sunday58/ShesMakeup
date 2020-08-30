package com.sundaydavid989.shesmakeup.data.db.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MakeupTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<ProductColor>? {
        val listType: Type = object : TypeToken<List<ProductColor?>?>(){}.type
        return Gson().fromJson<List<ProductColor>>(value, listType)
    }
    @TypeConverter
    fun listToString(list: List<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}