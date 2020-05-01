package com.mobile_app.themovie.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

class CatalogIdsConverter {

    @TypeConverter
    fun listToJson(value: Set<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toSet()
}