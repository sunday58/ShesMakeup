package com.sundaydavid989.shesmakeup.data.db.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MakeupItemTypeConverter {
    @TypeConverter
    fun fromString(value: String?): ArrayList<Makeup>? {
        val listType: Type = object : TypeToken<ArrayList<Makeup?>?>(){}.type
        return Gson().fromJson<ArrayList<Makeup>>(value, listType)
    }
    @TypeConverter
    fun listToString(list: ArrayList<Makeup?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}